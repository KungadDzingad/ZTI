package pl.karinawojtek.ztiserver.models.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CreateAuctionRequest {
    private String name;
    private double currentPrice;
    private double buyNowPrice;
    private boolean licitable;
    private String description;
    private String closingDate;
}
