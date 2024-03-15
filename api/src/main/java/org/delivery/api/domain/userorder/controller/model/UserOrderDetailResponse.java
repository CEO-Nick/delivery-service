package org.delivery.api.domain.userorder.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {
    
    private UserOrderResponse userOrderResponse;    // 사용자의 특정 주문 건
    
    private StoreResponse storeResponse;    // 해당 가게 정보
        
    private List<StoreMenuResponse> storeMenuResponseList;  // 해당 가게에서 주문한 메뉴 목록
}
