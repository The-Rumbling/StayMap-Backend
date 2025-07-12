package com.therumbling.staymap.iam.infrastructure.hashing.bcrypt;

import com.therumbling.staymap.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
