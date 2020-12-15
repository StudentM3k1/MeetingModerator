package Datenbank.Manager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Datenbank.ConnectionManager;
import Datenbank.Annotations.Key;
import Datenbank.Annotations.Spalte;
import Datenbank.Annotations.Spalte.SpaltenTyp;
import Datenbank.Annotations.Tabelle;

public abstract class SqlBase<TDbo> {
	
	protected abstract TDbo CreateNew();
	
	protected TDbo getFirst(List<TDbo> dbos) throws Exception {
		return dbos.size() > 0 ? dbos.get(0) : null;
	}
	
	public void CreateTabelle() throws Exception {
		String sql = this.CreateTabelleSql(CreateNew());		
		this.Execute(sql);
	}
	
	public List<TDbo> GetAll() throws Exception {
		List<TDbo> result = new ArrayList<TDbo>();
		
		Class<? extends Object> klasse = CreateNew().getClass();
		Tabelle tabelle = GetTabelle(klasse);
		
		String sql = "SELECT * FROM " + tabelle.Name();
			
		ResultSet resultSet = ExecuteQuery(sql);
		if(resultSet != null) {
			while(resultSet.next()) {
				TDbo newItem = CreateNew();
				
				for(Field field : klasse.getDeclaredFields()) {
					SetField(newItem, resultSet, field);
				}
				
				result.add(newItem);
			}
		}
		
		return result;
	}
	
	private TDbo SetField(TDbo dbo, ResultSet resultSet, Field field) throws Exception {
		SpaltenDaten spalte = GetSpalte(field);
		Object value = null;
		
		switch(spalte.getSpalte().Typ()) {
			case Int:
				value = resultSet.getInt(spalte.getSpalte().Name());
				break;
			case Datetime:
				value = resultSet.getDate(spalte.getSpalte().Name());
				break;
			case Time:
				value = resultSet.getTime(spalte.getSpalte().Name());
				break;
			case Varchar:
				value = resultSet.getString(spalte.getSpalte().Name());
				break;
		}
	
		field.set(dbo, value);
		
		return dbo;
	}
	
	public List<TDbo> GetBy(Bedingung bedingung) throws Exception 
	{
		List<Bedingung> list = new ArrayList<Bedingung>();
		list.add(bedingung);
		return GetBy(list);
	}
	
	public List<TDbo> GetBy(List<Bedingung> bedingungen) throws Exception
	{
		List<TDbo> result = new ArrayList<TDbo>();
		
		Class<? extends Object> klasse = CreateNew().getClass();
		Tabelle tabelle = GetTabelle(klasse);
		
		String sql = "SELECT * FROM " + tabelle.Name();
		List<SqlParameter> parameter = new ArrayList<SqlParameter>();
		boolean firstItem = true;		
		for(Bedingung bedingung : bedingungen) {
			if(firstItem) {
				sql += " WHERE ";
				firstItem = false;
			}
			else {
				sql += " AND ";
			}
			
			sql += bedingung.GetWhere();
			
			if(bedingung.NeedParameter()) {
				parameter.add(bedingung.GetParameter(parameter.size() + 1));
			}
		}
		
		Connection connection = ConnectionManager.GetConnection();	
		System.out.println(sql);			
		PreparedStatement statement = connection.prepareStatement(sql);
					
		for(SqlParameter para : parameter) {
			para.AddToStatemant(statement);
		}
		
		ResultSet resultSet = statement.executeQuery();
		if(resultSet != null) {
			while(resultSet.next()) {
				TDbo newItem = CreateNew();
				
				for(Field field : klasse.getDeclaredFields()) {
					SetField(newItem, resultSet, field);
				}
				
				result.add(newItem);
			}
		}
		
		return result;
	}
	
	protected int Add(TDbo dbo) throws Exception {
		Class<? extends Object> klasse = CreateNew().getClass();
		
		List<SpaltenDaten> spalten = GetSpalten(klasse);
		Tabelle tabelle = GetTabelle(klasse);
		
		String insertString = "INSERT INTO " + tabelle.Name() + " (";
		String valueString = "";
		
		List<SqlParameter> parameter = new ArrayList<SqlParameter>();
		
		boolean firstItem = true;
		for(SpaltenDaten spalte : spalten) {
			if(spalte.getKey() != null && spalte.getKey().Autoincrement()) {
				continue;
			}
			
			if(firstItem) {
				firstItem = false;
			}
			else {
				insertString += ", ";
				valueString += ", ";
			}
			
			insertString += spalte.getSpalte().Name();
			//spalte.get
			
			if(spalte.getSpalte().Typ() == SpaltenTyp.Varchar || spalte.getSpalte().Typ() == SpaltenTyp.Time) {
				valueString += "?";
				parameter.add(new SqlParameter(spalte.getField().get(dbo), 
						spalte.getSpalte().Typ(), parameter.size() + 1));
			}				
			else {
				valueString += spalte.getField().get(dbo);
			}
			
		}
		 
		Connection connection = ConnectionManager.GetConnection();	
		String sql = insertString + ") VALUES (" + valueString + ")";
		System.out.println(sql);			
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					
		for(SqlParameter para : parameter) {
			para.AddToStatemant(statement);
		}
		
		statement.execute();	
		ResultSet resultSet = statement.getGeneratedKeys(); //TODO
		if(resultSet != null) {
			while(resultSet.next()) {
				return resultSet.getInt(1);
			}
		}
		
		return 0;
	}
	
	public void Delete(TDbo dbo) throws Exception {
		Class<? extends Object> klasse = CreateNew().getClass();		
		List<SpaltenDaten> spalten = GetSpalten(klasse);
		Tabelle tabelle = GetTabelle(klasse);		
		List<SpaltenDaten> keySpalten = GetKeySpalten(spalten);
		
		if(keySpalten.size() > 0) {
			String sql = "DELETE FROM " + tabelle.Name() + " WHERE ";
			
			boolean firstItem = true;
			for(SpaltenDaten spalte : keySpalten) {				
				if(firstItem) {
					firstItem = false;
				}
				else {
					sql += " AND ";
				}
				
				sql += tabelle.Name() + "." + spalte.getSpalte().Name() + " = " + spalte.getField().get(dbo);
			}
			
			Execute(sql);
		}
	}
	
	public void Update(TDbo dbo) throws Exception {
		Class<? extends Object> klasse = CreateNew().getClass();		
		List<SpaltenDaten> spalten = GetSpalten(klasse);
		Tabelle tabelle = GetTabelle(klasse);
		
		String sql = "UPDATE " + tabelle.Name() + " SET ";
		String setSql = "";
		String whereSql = "";
		List<SqlParameter> parameter = new ArrayList<SqlParameter>();
		boolean firstSet = true;
		boolean firstWhere = true;
		
		for(SpaltenDaten spalte : spalten) {
			if(spalte.getKey() != null) {				
				if(firstWhere) {
					firstWhere = false;
				}
				else {
					whereSql += " AND ";
				}
				
				if(spalte.getSpalte().Typ() == SpaltenTyp.Varchar || spalte.getSpalte().Typ() == SpaltenTyp.Time) {
					whereSql += spalte.getSpalte().Name() + " = ?";
					parameter.add(new SqlParameter(spalte.getField().get(dbo), 
							spalte.getSpalte().Typ(), parameter.size() + 1));
				}				
				else {
					whereSql += spalte.getSpalte().Name() + " = " + spalte.getField().get(dbo);
				}
			}
			else {
				if(firstSet) {
					firstSet = false;
				}
				else {
					setSql += ", ";
				}
				
				if(spalte.getSpalte().Typ() == SpaltenTyp.Varchar || spalte.getSpalte().Typ() == SpaltenTyp.Time) {
					setSql += spalte.getSpalte().Name() + " = ?";
					parameter.add(new SqlParameter(spalte.getField().get(dbo), 
							spalte.getSpalte().Typ(), parameter.size() + 1));
				}				
				else {
					setSql += spalte.getSpalte().Name() + " = " + spalte.getField().get(dbo);
				}
			}
		}
		
		sql += setSql + " WHERE " + whereSql;
		
		Connection connection = ConnectionManager.GetConnection();	
		System.out.println(sql);			
		PreparedStatement statement = connection.prepareStatement(sql);
					
		for(SqlParameter para : parameter) {
			para.AddToStatemant(statement);
		}
		
		statement.execute();	
	}
	
	public String CreateTabelleSql(TDbo dbo) {
		String result = "";
		
		Class<? extends Object> klasse = dbo.getClass();
		Tabelle tabelle  = GetTabelle(klasse);
		
		if(tabelle != null) {
			List<SpaltenDaten> spalten = GetSpalten(klasse);
			
			if(spalten.size() > 0) {
				result += "CREATE TABLE " + tabelle.Name() + " (";
				
				boolean firstItem = true;
				for(SpaltenDaten spalteDaten : spalten) {
					
					if(firstItem) {
						firstItem = false;
					}
					else {
						result += ", ";
					}
					
					result += spalteDaten.getSpalte().Name() + " " + 
							GetDataType(spalteDaten.getSpalte()) + " " + 
							(spalteDaten.getSpalte().AllowNull() ? "NULL" : "NOT NULL");
					
					if(spalteDaten.getKey() != null) {
						result += (spalteDaten.getKey().Autoincrement() ? " AUTO_INCREMENT" : "") + " PRIMARY KEY";
					}
				}
				
				result += ");";
			}
		}
		
		return result;
	}
	
	public ResultSet ExecuteQuery(String sql) throws Exception {
		Connection connection = ConnectionManager.GetConnection();			
		System.out.println(sql);
		
		Statement statement = connection.createStatement();
		return statement.executeQuery(sql);
	}
	
	public void Execute(String sql) throws Exception {
		Connection connection = ConnectionManager.GetConnection();			
		System.out.println(sql);
		
		Statement statement = connection.createStatement();
		statement.execute(sql);
	}
	
	private String GetDataType(Spalte spalte) {
		switch(spalte.Typ()) {
			case Int:
				return "INT";
			case Datetime:
				return "DATETIME";
			case Time:
				return "TIME";
			case Varchar:
				if(spalte.MaxLength() > 0) {
					return "VARCHAR" + "(" + spalte.MaxLength() + ")";
				}
				return "TEXT";
			default:
				return "";
		}
	}
	
	private Tabelle GetTabelle(Class<? extends Object> klasse)
	{
		Tabelle tabelle = klasse.getAnnotation(Tabelle.class);
		return tabelle;
	}
	
	private SpaltenDaten GetSpalte(Field field)
	{
		Spalte spalte = field.getAnnotation(Spalte.class);
		Key key = field.getAnnotation(Key.class);
		
		return spalte == null ? null : new SpaltenDaten(spalte, key, field);
	}
	
	private List<SpaltenDaten> GetSpalten(Class<? extends Object> klasse)
	{
		List<SpaltenDaten> spalten = new ArrayList<SpaltenDaten>();
		
		for(Field field : klasse.getDeclaredFields()) {
			SpaltenDaten daten = GetSpalte(field);
			if(daten != null) {
				spalten.add(daten);
			}
		}
		
		return spalten;
	}
	
	private List<SpaltenDaten> GetKeySpalten(List<SpaltenDaten> spalten) {
		List<SpaltenDaten> result = new ArrayList<SpaltenDaten>();
		
		for(SpaltenDaten spalte : spalten) {
			if(spalte.getKey() != null) {
				result.add(spalte);
			}
		}
		
		return result;
	}
}
