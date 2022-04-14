package pl.karinawojtek.ztiserver.models.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateReviewRequest {
    private String description;
    private int mark;
}
