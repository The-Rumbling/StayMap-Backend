package com.therumbling.staymap.iam.application.internal.commandservices;

import com.therumbling.staymap.iam.application.internal.outboundservices.hashing.HashingService;
import com.therumbling.staymap.iam.application.internal.outboundservices.tokens.TokenService;
import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.iam.domain.model.commands.SignInCommand;
import com.therumbling.staymap.iam.domain.model.commands.SignUpCommand;
import com.therumbling.staymap.iam.domain.model.commands.UpdateUserCommand;
import com.therumbling.staymap.iam.domain.services.UserCommandService;
import com.therumbling.staymap.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());

        if (user.isEmpty())
            throw new RuntimeException("User not found");

        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");

        var token = tokenService.generateToken(user.get().getUsername());
        
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");

        var user = new User(command.username(), hashingService.encode(command.password()), command.role(), command.profileImage());
        userRepository.save(user);

        return userRepository.findByUsername(command.username());
    }

    @Override
    public Optional<User> handle(UpdateUserCommand command) {
        var result = userRepository.findById(command.userId());
        if (result.isEmpty())
            throw new IllegalArgumentException("User with id %s not found".formatted(command.userId()));
        var userToUpdate = result.get();
        try {
            var updatedUser = userRepository.save(userToUpdate.updateInformation(command.username(), command.profileImage()));
            return Optional.of(updatedUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating user: %s".formatted(e.getMessage()));
        }
    }
}
