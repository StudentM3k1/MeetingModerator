package basic;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/Projekt/{value}")
public class HW {

 @GET
 @Produces({MediaType.TEXT_PLAIN})
 public String get() {
 return "Working fine";
 }
}