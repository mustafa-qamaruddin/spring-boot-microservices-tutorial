package microservices.book.gamification.event;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


/**
 * Event received when a multiplication has been solved in the system.
 * Provides some context information about the multiplication.
 */
@Builder
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Value
@JsonDeserialize(builder = MultiplicationSolvedEvent.MultiplicationSolvedEventBuilder.class)
class MultiplicationSolvedEvent implements Serializable {

    private final Long multiplicationResultAttemptId;
    private final Long userId;
    private final boolean correct;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MultiplicationSolvedEventBuilder {
    }

}