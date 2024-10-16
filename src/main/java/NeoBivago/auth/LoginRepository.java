package NeoBivago.auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import NeoBivago.models.UserModel;

@Repository
public interface LoginRepository extends JpaRepository<UserModel, UUID> {
    
    public Optional<UserModel> findByEmail(String login);

}
