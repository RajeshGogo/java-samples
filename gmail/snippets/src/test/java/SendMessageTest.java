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


import com.google.api.services.gmail.model.Message;
import org.junit.Test;
import javax.mail.MessagingException;
import java.io.IOException;
import static org.junit.Assert.assertNotNull;

public class SendMessageTest extends BaseTest{

    @Test
    public void testSendEmail() throws MessagingException, IOException {
        Message message=SendMessage.sendEmail(this.service,TEST_USER,RECIPIENT);
        assertNotNull(message);
        this.service.users().messages().trash("me", message.getId());
    }
}