// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


// [START classroom_create_course]
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.classroom.Classroom;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.Course;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.Collections;

public class CreateCourse {
    /**
     * Creates a course
     *
     * @return newly created course
     * @throws IOException -  if credentials file not found.
     */
    public static Course createCourse() throws IOException {
        /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
            guides on implementing OAuth2 for your application. */
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Collections.singleton(ClassroomScopes.CLASSROOM_COURSES));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);

        // Create the classroom API client
        Classroom service = new Classroom.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Classroom samples")
                .build();
        try {
            Course course = new Course()
                    .setName("10th Grade Biology")
                    .setSection("Period 2")
                    .setDescriptionHeading("Welcome to 10th Grade Biology")
                    .setDescription("We'll be learning about about the structure of living creatures "
                            + "from a combination of textbooks, guest lectures, and lab work. Expect "
                            + "to be excited!")
                    .setRoom("301")
                    .setOwnerId("me")
                    .setCourseState("PROVISIONED");
            course = service.courses().create(course).execute();
            System.out.printf("Course created: %s (%s)\n", course.getName(), course.getId());
            return course;
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to create course: " + e.getDetails());
            throw e;
        }
    }
}
// [END classroom_create_course]