package org.openchat.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openchat.api.createNewPostApi.CreateNewPostResponsePresenter;
import org.openchat.entities.Post;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class JsonFormatterListObjectsTest {

    private JsonFormatterListObjects jsonFormatterListObjects;

    @BeforeEach
    void setUp() {
        jsonFormatterListObjects = new JsonFormatterListObjects();
    }

    @Test
    void given_a_list_of_objects_it_will_convert_them_to_json_array() throws Exception {
        //arrange
        FormatDateService formatDateService = new FormatDateService();
        CreateNewPostResponsePresenter createNewPostResponsePresenter = new CreateNewPostResponsePresenter(formatDateService);
        List<Map<String, String>> maps = Arrays.asList(
                createNewPostResponsePresenter.apply(new Post("test_post_id", "test_user_id", "test_text", LocalDateTime.of(2020, 1, 1, 1, 1, 1))),
                createNewPostResponsePresenter.apply(new Post("test_post_id", "test_user_id", "test_text", LocalDateTime.of(2020, 1, 1, 1, 1, 1)))
        );

        //act
        String jsonValues = jsonFormatterListObjects.render(maps);

        //assert
        Assertions.assertThat(jsonValues).isEqualTo(getJsonArray());
    }

    private String getJsonArray() {
        return "[{\"dateTime\":\"2020-01-01T01:01:01Z\",\"postId\":\"test_post_id\",\"text\":\"test_text\",\"userId\":\"test_user_id\"},{\"dateTime\":\"2020-01-01T01:01:01Z\",\"postId\":\"test_post_id\",\"text\":\"test_text\",\"userId\":\"test_user_id\"}]";
    }

}