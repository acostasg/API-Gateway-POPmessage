package api.infrastucture.elasticSearch;

import api.domain.entity.Token;
import api.domain.entity.User;

public class TokenRepository extends AbstractElasticSearchRepository implements api.domain.infrastructure.TokenRepository {

    private static final String index = "token_index";
    private static final String type = "token";

    @Override
    public Token generateToken(User user) {
        return null;
    }

    @Override
    public Token validateToken(Token token) {
        return null;
    }
}
