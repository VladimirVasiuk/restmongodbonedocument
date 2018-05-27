package com.solution.restmongodbonedocument.web;

import com.google.common.collect.ImmutableList;
import com.solution.restmongodbonedocument.model.entity.RetailerGroup;
import com.solution.restmongodbonedocument.model.entity.RetailerIntegration;
import com.solution.restmongodbonedocument.model.entity.RetailerProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RetailerIntegrationsServiceControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setUp() {
        mongoTemplate.dropCollection(RetailerIntegration.class);
        mongoTemplate.save(new RetailerIntegration("integrationId1", "1st Integration", "1st description",
                        Arrays.asList(
                                new RetailerGroup("groupId101", "101st Group", "101st description",
                                        Arrays.asList(
                                                new RetailerProfile("profileId1101", "1101st Profile", "1101st description"),
                                                new RetailerProfile("profileId1102", "1101st Profile", "1101st description"),
                                                new RetailerProfile("profileId1102", "1101st Profile", "1101st description")
                                        )
                                ),
                                new RetailerGroup("groupId102", "102nd Group", "102nd description",
                                        Arrays.asList(
                                                new RetailerProfile("profileId1201", "1201st Profile", "1201st description"),
                                                new RetailerProfile("profileId1202", "1202st Profile", "1202nd description"),
                                                new RetailerProfile("profileId1203", "1203rd Profile", "1203rd description")
                                        )
                                ),
                                new RetailerGroup("groupId103", "103rd Group", "103rd description",
                                        Arrays.asList(
                                                new RetailerProfile("profileId1301", "1301st Profile", "1301st description"),
                                                new RetailerProfile("profileId1302", "1302st Profile", "1302nd description"),
                                                new RetailerProfile("profileId1303", "1303rd Profile", "1303rd description")
                                        )
                                )
                        )
                )
        );
        mongoTemplate.save(new RetailerIntegration("integrationId2", "2nd Integration", "2nd description",
                        Arrays.asList(
                                new RetailerGroup("groupId201", "201st Group", "201st description",
                                        Arrays.asList(
                                                new RetailerProfile("profileId2101", "2101st Profile", "2101st description"),
                                                new RetailerProfile("profileId2102", "2101st Profile", "2101st description")
                                        )
                                ),
                                new RetailerGroup("groupId202", "202nd Group", "202nd description",
                                        Arrays.asList(
                                                new RetailerProfile("profileId2201", "2201st Profile", "2201st description"),
                                                new RetailerProfile("profileId2202", "2202st Profile", "2202nd description")
                                        )
                                )
                        )
                )
        );
        mongoTemplate.save(new RetailerIntegration("integrationId3", "3rd Integration", "3rd description",
                Collections.singletonList(
                        new RetailerGroup("groupId301", "301st Group", "301st description",
                                Collections.singletonList(
                                        new RetailerProfile("profileId3101", "3101st Profile", "3101st description")
                                )
                        )
                )
                )
        );
    }

    @Test
    public void insertRetailerIntegration() throws Exception {
        RetailerIntegration integration = new RetailerIntegration("4th Integration", "4th description", new ArrayList<>());
        HttpEntity<RetailerIntegration> httpEntity = new HttpEntity<>(integration);
        ResponseEntity<RetailerIntegration> responseEntity1 =
                restTemplate.exchange("/api/retailerIntegrations/", HttpMethod.POST,
                        httpEntity, new ParameterizedTypeReference<RetailerIntegration>() {
                        });
        assertThat(responseEntity1.getStatusCode().value(), is(200));
        RetailerIntegration retailerIntegration = responseEntity1.getBody();
        assertNotNull(retailerIntegration);
        assertNotNull(retailerIntegration.getId());
        assertEquals(retailerIntegration.getName(), integration.getName());
        assertEquals(retailerIntegration.getDescription(), integration.getDescription());

        ResponseEntity<List<RetailerIntegration>> responseEntity2 =
                restTemplate.exchange("/api/retailerIntegrations/", HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<RetailerIntegration>>() {
                        });
        List<RetailerIntegration> actualList = responseEntity2.getBody();
        assertNotNull(actualList);
        assertThat(actualList.size(), is(4));
        List<String> actualIds = actualList.stream()
                .map(RetailerIntegration::getId)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
        System.out.println(actualIds);
    }

    @Test
    public void getAllRetailerIntegration() throws Exception {
        ResponseEntity<List<RetailerIntegration>> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/", HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<RetailerIntegration>>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
        List<RetailerIntegration> actualList = responseEntity.getBody();
        assertNotNull(actualList);
        assertThat(actualList.size(), is(3));
        List<String> actualNames = actualList.stream()
                .map(RetailerIntegration::getName)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
        assertThat(actualNames, containsInAnyOrder("1st Integration", "2nd Integration", "3rd Integration"));
        List<String> actualIds = actualList.stream()
                .map(RetailerIntegration::getId)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
        assertThat(actualIds, containsInAnyOrder("integrationId1", "integrationId2", "integrationId3"));
    }

    @Test
    public void getRetailerIntegrationById() {
        ResponseEntity<RetailerIntegration> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId1", HttpMethod.GET,
                        null, new ParameterizedTypeReference<RetailerIntegration>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
        RetailerIntegration integration = responseEntity.getBody();
        assertNotNull(integration);
        assertEquals(integration.getName(), "1st Integration");
        assertThat(integration.getRetailerGroups().size(), is(3));
    }

    @Test
    public void getRetailerIntegrationWrongId() {
        ResponseEntity<RetailerIntegration> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/nonExistId", HttpMethod.GET,
                        null, new ParameterizedTypeReference<RetailerIntegration>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void updateRetailerIntegration() {
        RetailerIntegration integration = new RetailerIntegration("integrationId3", "New 3rd Integration", "Changed 3rd description",
                Collections.singletonList(
                        new RetailerGroup("groupId301", "301st Group", "301st description",
                                Collections.singletonList(
                                        new RetailerProfile("profileId3101", "3101st Profile", "3101st description")
                                )
                        )
                )
        );
        HttpEntity<RetailerIntegration> httpEntity = new HttpEntity<>(integration);
        ResponseEntity<RetailerIntegration> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId3", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<RetailerIntegration>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
        RetailerIntegration retailerIntegration = responseEntity.getBody();
        assertNotNull(retailerIntegration);
        assertEquals(retailerIntegration.getName(), "New 3rd Integration");
        assertThat(retailerIntegration.getRetailerGroups().size(), is(1));

    }

    @Test
    public void deleteRetailerIntegration() {
        ResponseEntity<RetailerIntegration> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId1", HttpMethod.DELETE,
                        null, new ParameterizedTypeReference<RetailerIntegration>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
    }

    @Test
    public void getAllRetailerGroupByRetailerIntegrationId() {
        ResponseEntity<List<RetailerGroup>> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId1/groups", HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<RetailerGroup>>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
        List<RetailerGroup> actualList = responseEntity.getBody();
        assertNotNull(actualList);
        assertThat(actualList.size(), is(3));
        List<String> actualNames = actualList.stream()
                .map(RetailerGroup::getName)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
        assertThat(actualNames, containsInAnyOrder("101st Group", "102nd Group", "103rd Group"));
    }

    @Test
    public void insertRetailerGroup() {
        RetailerGroup group = new RetailerGroup("302nd Group", "302nd description",
                Collections.singletonList(
                        new RetailerProfile("profileId3201", "3201st Profile", "3201st description")
                )
        );
        HttpEntity<RetailerGroup> httpEntity = new HttpEntity<>(group);
        ResponseEntity<RetailerGroup> responseEntity1 =
                restTemplate.exchange("/api/retailerIntegrations/integrationId3/groups", HttpMethod.POST,
                        httpEntity, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity1.getStatusCode().value(), is(200));
        RetailerGroup retailerGroup = responseEntity1.getBody();
        assertNotNull(retailerGroup);
        assertNotNull(retailerGroup.getId());
        assertEquals(retailerGroup.getName(), group.getName());
        assertEquals(retailerGroup.getDescription(), group.getDescription());
    }

    @Test
    public void insertRetailerGroupWrongIntegrationId() {
        RetailerGroup group = new RetailerGroup("302nd Group", "302nd description",
                Collections.singletonList(
                        new RetailerProfile("profileId3201", "3201st Profile", "3201st description")
                )
        );
        HttpEntity<RetailerGroup> httpEntity = new HttpEntity<>(group);
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/nonexistId/groups", HttpMethod.POST,
                        httpEntity, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void getRetailerGroupById() {
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId2/groups/groupId202", HttpMethod.GET,
                        null, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
        RetailerGroup group = responseEntity.getBody();
        assertNotNull(group);
        assertEquals(group.getName(), "202nd Group");
        assertThat(group.getRetailerProfiles().size(), is(2));
    }

    @Test
    public void getRetailerGroupByIdWrongIntId() {
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/wrongId/groups/groupId202", HttpMethod.GET,
                        null, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void getRetailerGroupByWrongId() {
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId2/groups/wrongId", HttpMethod.GET,
                        null, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void updateRetailerGroup() {
        RetailerGroup group = new RetailerGroup("groupId301", "New 302nd Group", "Change 302nd description",
                Collections.singletonList(
                        new RetailerProfile("profileId3201", "3201st Profile", "3201st description")
                )
        );
        HttpEntity<RetailerGroup> httpEntity = new HttpEntity<>(group);
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId3/groups/groupId301", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
        RetailerGroup retailerGroup = responseEntity.getBody();
        assertNotNull(retailerGroup);
        assertEquals(retailerGroup.getName(), "New 302nd Group");
        assertThat(retailerGroup.getRetailerProfiles().size(), is(1));
    }

    @Test
    public void updateRetailerGroupWrongIntegrId() {
        RetailerGroup group = new RetailerGroup("groupId301", "New 302nd Group", "Change 302nd description",
                Collections.singletonList(
                        new RetailerProfile("profileId3201", "3201st Profile", "3201st description")
                )
        );
        HttpEntity<RetailerGroup> httpEntity = new HttpEntity<>(group);
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/wrongId/groups/groupId301", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void updateRetailerGroupWrongId() {
        RetailerGroup group = new RetailerGroup("groupId301", "New 302nd Group", "Change 302nd description",
                Collections.singletonList(
                        new RetailerProfile("profileId3201", "3201st Profile", "3201st description")
                )
        );
        HttpEntity<RetailerGroup> httpEntity = new HttpEntity<>(group);
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId3/groups/wrongId", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void deleteRetailerGroup() {
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId1/groups/groupId103", HttpMethod.DELETE,
                        null, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));

    }

    @Test
    public void getAllRetailerProfileByRetailerIntegrationId() {
        ResponseEntity<List<RetailerProfile>> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId1/groups/groupId102/profile", HttpMethod.GET,
                        null, new ParameterizedTypeReference<List<RetailerProfile>>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
        List<RetailerProfile> actualList = responseEntity.getBody();
        assertNotNull(actualList);
        assertThat(actualList.size(), is(3));
        List<String> actualNames = actualList.stream()
                .map(RetailerProfile::getName)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
        assertThat(actualNames, containsInAnyOrder("1201st Profile", "1202st Profile", "1203rd Profile"));
    }

    @Test
    public void insertRetailerProfile() {
        RetailerProfile profile = new RetailerProfile("3102st Profile", "3102st description");
        HttpEntity<RetailerProfile> httpEntity = new HttpEntity<>(profile);
        ResponseEntity<RetailerProfile> responseEntity1 =
                restTemplate.exchange("/api/retailerIntegrations/integrationId3/groups/groupId301/profile", HttpMethod.POST,
                        httpEntity, new ParameterizedTypeReference<RetailerProfile>() {
                        });
        assertThat(responseEntity1.getStatusCode().value(), is(200));
        RetailerProfile retailerProfile = responseEntity1.getBody();
        assertNotNull(retailerProfile);
        assertNotNull(retailerProfile.getId());
        assertEquals(retailerProfile.getName(), profile.getName());
        assertEquals(retailerProfile.getDescription(), profile.getDescription());
    }

    @Test
    public void insertRetailerProfileWrongIntegrId() {
        RetailerProfile profile = new RetailerProfile("3102st Profile", "3102st description");
        HttpEntity<RetailerProfile> httpEntity = new HttpEntity<>(profile);
        ResponseEntity<RetailerProfile> responseEntity1 =
                restTemplate.exchange("/api/retailerIntegrations/wrongId/groups/groupId301/profile", HttpMethod.POST,
                        httpEntity, new ParameterizedTypeReference<RetailerProfile>() {
                        });
        assertThat(responseEntity1.getStatusCode().value(), is(404));
    }

    @Test
    public void insertRetailerProfileWrongGroupId() {
        RetailerProfile profile = new RetailerProfile("3102st Profile", "3102st description");
        HttpEntity<RetailerProfile> httpEntity = new HttpEntity<>(profile);
        ResponseEntity<RetailerProfile> responseEntity1 =
                restTemplate.exchange("/api/retailerIntegrations/integrationId3/groups/wrongId/profile", HttpMethod.POST,
                        httpEntity, new ParameterizedTypeReference<RetailerProfile>() {
                        });
        assertThat(responseEntity1.getStatusCode().value(), is(404));
    }

    @Test
    public void getRetailerProfileById() {
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId2/groups/groupId202/profile/profileId2202", HttpMethod.GET,
                        null, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
        RetailerGroup group = responseEntity.getBody();
        assertNotNull(group);
        assertEquals(group.getName(), "2202st Profile");
        assertEquals(group.getDescription(), "2202nd description");
    }

    @Test
    public void getRetailerProfileByIdWrongIntegrId() {
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/wrongId/groups/groupId202/profile/profileId2202", HttpMethod.GET,
                        null, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void getRetailerProfileByIdWrongGroupId() {
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId2/groups/wrongId/profile/profileId2202", HttpMethod.GET,
                        null, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void getRetailerProfileByIdWrongProfileId() {
        ResponseEntity<RetailerGroup> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId2/groups/groupId202/profile/wrongId", HttpMethod.GET,
                        null, new ParameterizedTypeReference<RetailerGroup>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void updateRetailerProfile() {
        RetailerProfile profile = new RetailerProfile("profileId3101", "New 3201st Profile", "Changed 3201st description");
        HttpEntity<RetailerProfile> httpEntity = new HttpEntity<>(profile);
        ResponseEntity<RetailerProfile> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId3/groups/groupId301/profile/profileId3101", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<RetailerProfile>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
        RetailerProfile retailerProfile = responseEntity.getBody();
        assertNotNull(retailerProfile);
        assertEquals(retailerProfile.getName(), "New 3201st Profile");
        assertEquals(retailerProfile.getDescription(), "Changed 3201st description");
    }

    @Test
    public void updateRetailerProfileWrongIntegrId() {
        RetailerProfile profile = new RetailerProfile("profileId3101", "New 3201st Profile", "Changed 3201st description");
        HttpEntity<RetailerProfile> httpEntity = new HttpEntity<>(profile);
        ResponseEntity<RetailerProfile> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/wrongId/groups/groupId301/profile/profileId3101", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<RetailerProfile>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void updateRetailerProfileWrongGroupId() {
        RetailerProfile profile = new RetailerProfile("profileId3101", "New 3201st Profile", "Changed 3201st description");
        HttpEntity<RetailerProfile> httpEntity = new HttpEntity<>(profile);
        ResponseEntity<RetailerProfile> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId3/groups/wrongId/profile/profileId3101", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<RetailerProfile>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void updateRetailerProfileWrongProfileId() {
        RetailerProfile profile = new RetailerProfile("profileId3101", "New 3201st Profile", "Changed 3201st description");
        HttpEntity<RetailerProfile> httpEntity = new HttpEntity<>(profile);
        ResponseEntity<RetailerProfile> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId3/groups/groupId301/profile/wrongId", HttpMethod.PUT,
                        httpEntity, new ParameterizedTypeReference<RetailerProfile>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(404));
    }

    @Test
    public void deleteRetailerProfile() {
        ResponseEntity<RetailerProfile> responseEntity =
                restTemplate.exchange("/api/retailerIntegrations/integrationId1/groups/groupId103/profile/profileId1303", HttpMethod.DELETE,
                        null, new ParameterizedTypeReference<RetailerProfile>() {
                        });
        assertThat(responseEntity.getStatusCode().value(), is(200));
    }
}