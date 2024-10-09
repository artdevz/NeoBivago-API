package NeoBivago.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import NeoBivago.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    UserDetails findByUserEmail(String userEmail);
    UserModel findByUserCPF(String userCPF);

} 