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


import com.google.api.services.gmail.model.SmimeInfo;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

// Unit testcase for gmail create smime info snippet
public class TestCreateSmimeInfo {

    @Test
    public void testCreateSmimeInfo() {
        SmimeInfo smimeInfo = CreateSmimeInfo.createSmimeInfo(
                "files/cert.p12",
                null /* password */);

        assertNotNull(smimeInfo);
        assertNull(smimeInfo.getEncryptedKeyPassword());
        assertNull(smimeInfo.getExpiration());
        assertNull(smimeInfo.getId());
        assertNull(smimeInfo.getIsDefault());
        assertNull(smimeInfo.getIssuerCn());
        assertNull(smimeInfo.getPem());
        assertThat(smimeInfo.getPkcs12().length(), greaterThan(0));
    }

    @Test
    public void testCreateSmimeInfoWithPassword() {
        SmimeInfo smimeInfo = CreateSmimeInfo.createSmimeInfo(
                "files/cert.p12",
                "certpass");

        assertNotNull(smimeInfo);
        assertEquals(smimeInfo.getEncryptedKeyPassword(), "certpass");
        assertNull(smimeInfo.getExpiration());
        assertNull(smimeInfo.getId());
        assertNull(smimeInfo.getIsDefault());
        assertNull(smimeInfo.getIssuerCn());
        assertNull(smimeInfo.getPem());
        assertThat(smimeInfo.getPkcs12().length(), greaterThan(0));
    }

    @Test
    public void testCreateSmimeInfoFileNotFound() {
        SmimeInfo smimeInfo = CreateSmimeInfo.createSmimeInfo(
                "files/notfound.p12",
                null /* password */);
        assertNull(smimeInfo);
    }
}
