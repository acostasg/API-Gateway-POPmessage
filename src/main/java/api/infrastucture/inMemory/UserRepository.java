package api.infrastucture.inMemory;

import api.domain.entity.Id;
import api.domain.entity.Status;
import api.domain.entity.User;

public class UserRepository implements api.domain.infrastructure.UserRepository {

    @Override
    public User registerUser(String name, String dateOfBirth, String userName, String password )
    {
        return this.dummy(name,userName,password);
    }

    @Override
    public User loginUser(String userName, String password){
        return this.dummy("dummyName",userName,password);
    }

    private User dummy(String name,String userName,String password)
    {
        return new User(
                new Id("id_dummy"),
                name,
                userName,
                password,
                Status.ACTIVE
        );
    }

}
