package org.openchat.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openchat.api.createNewPostApi.CreateNewPostResponsePresenter;
import org.openchat.entities.Post;

import java.time.LocalDateTime;
import java.util.Map;


public class JsonFormatterSingleObjectTest {

    private JsonFormatterSingleObject jsonFormatterSingleObject;

    @BeforeEach
    void setUp() {
        jsonFormatterSingleObject = new JsonFormatterSingleObject();
    }

    @Test
    void given_an_map_of_fields_and_values_it_will_convert_it_into_a_json_object() throws Exception {
        //arrange
        Post post = new Post("test_post_id", "test_user_id", "test_text", LocalDateTime.of(2020, 1, 1, 1, 1, 1));
        FormatDateService formatDateService = new FormatDateService();
        CreateNewPostResponsePresenter createNewPostResponsePresenter = new CreateNewPostResponsePresenter(formatDateService);
        Map<String, String> map = createNewPostResponsePresenter.apply(post);

        //act
        String jsonValue = jsonFormatterSingleObject.render(map);

        //assert
        Assertions.assertThat(jsonValue).isEqualTo(getJson());
    }


    private String getJson() {
        return "{\"dateTime\":\"2020-01-01T01:01:01Z\",\"postId\":\"test_post_id\",\"text\":\"test_text\",\"userId\":\"test_user_id\"}";
    }
}
