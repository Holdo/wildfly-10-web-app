package cz.muni.fi.pv243.rest;

import java.util.List;
import javax.inject.Inject;
import model.Demo;
import dao.DemoDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author skylar
 */
public class DemoResourceImpl implements DemoResource {

    @Inject
    private DemoDAO demoDao;

    @GET
    @Path("/getAll")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Demo> findAll() {
        return demoDao.findAll();
    }

    @GET
    @Path("/artist/{artist}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Demo> findAllFromArtist(String artist) {
        return demoDao.findDemos(artist);
    }

    @GET
    @Path("/title/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public Demo findByTitle(String title) {
        return demoDao.findDemo(title);
    }

    /*@PUT
    @Path("/demo/add/{artist}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void addDemo(String artist, MultipartFormDataInput input) {
        Demo demo = new Demo();
        String fileName = "";
        Map<String, List<InputPart>> formParts = input.getFormDataMap();

        List<InputPart> inPart = formParts.get("file");  
        for (InputPart inputPart : inPart) {
            try {
                // Retrieve headers, read the Content-Disposition header to obtain the original name of the file
                MultivaluedMap<String, String> headers = inputPart.getHeaders();
                String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
                for (String name : contentDispositionHeader) {
                    if ((name.trim().startsWith("filename"))) {
                        String[] tmp = name.split("=");
                        fileName = tmp[1].trim().replaceAll("\"", "");
                    }
                }
                InputStream istream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(istream);
                writeFile(bytes, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        demo.setTitle(fileName);
        demo.setArtist(artist);
        demo.setStatus(Demo.Status.UPLOADED);
        demoDao.createDemo(demo);
    }*/

    @DELETE
    @Path("/demo/delete/{title}")
    public void removeDemo(String title) {
        demoDao.deleteDemo(demoDao.findDemo(title));
    }

    private void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
    }
}
