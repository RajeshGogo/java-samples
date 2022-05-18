import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.Course;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.Collections;

 // Base class for integration tests.
public class BaseTest {
     protected Classroom service;
     protected Course testCourse;

    /**
     * Creates a default authorization Classroom client service.
     *
     * @return an authorized Classroom client service
     * @throws IOException - if credentials file not found.
     */
    protected Classroom buildService() throws IOException {
        /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
            guides on implementing OAuth2 for your application. */
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Collections.singleton(ClassroomScopes.CLASSROOM_ROSTERS));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);

        // Create the classroom API client
        Classroom service = new Classroom.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Classroom Snippets")
                .build();

        return service;
    }

    @Before
    public void setup() throws IOException{
        this.service = buildService();
        this.testCourse = CreateCourse.createCourse();
    }

    @After
    public void tearDown() throws IOException{
        deleteCourse(this.testCourse.getId());
        this.testCourse = null;

    }

     public void deleteCourse(String courseId) throws IOException {
         this.service.courses().delete(courseId).execute();
     }
}
