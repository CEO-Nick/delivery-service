package org.delivery.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * token에 대한 domain logic만
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    // JWT token helper를 쓸 수도 있고, 추후 UUID로 만드는 token helper를 쓸 수도 있다 -> 갈아끼워 주면 된다
    private final TokenHelperIfs tokenHelperIfs;

    // userId를 받아서 token 만들어서 return (매개변수는 변경될 수 있음)
    public TokenDto issueAccessToken(Long userId) {

        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", userId);

        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("userId", userId);

        return tokenHelperIfs.issueRefreshToken(data);
    }

    // token validation하고 로직에 사용할 값 리턴
    public Long validationToken(String token) {
        // validationTokenWithThrow의 결과에는 token을 파싱하고 그 결과를 map 형태로 넣은게 return
        Map<String, Object> map = tokenHelperIfs.validationTokenWithThrow(token);
        Object userId = map.get("userId");

        // userId 없을 수 있음 -> null check
        Objects.requireNonNull(userId, () -> {throw new ApiException(ErrorCode.NULL_POINT);});

        return Long.parseLong(userId.toString());
    }
}
