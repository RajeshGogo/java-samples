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

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

// Unit testcase for copy presentation snippet
public class TestCopyPresentation extends BaseTest{

    @Test
    public void testCopyPresentation() throws IOException {
        String presentationId = createTestPresentation();
        String copyId = CopyPresentation.copyPresentation(presentationId, "My Duplicate Presentation");
        assertNotNull(copyId);
        deleteFileOnCleanup(copyId);
    }
}
