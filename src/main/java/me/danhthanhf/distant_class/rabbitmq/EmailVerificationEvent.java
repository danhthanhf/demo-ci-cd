package me.danhthanhf.distant_class.rabbitmq;

import java.io.Serializable;

public record EmailVerificationEvent(String email, String token) implements Serializable {
}
