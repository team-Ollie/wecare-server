package ollie.wecare.user.dto;

public record LoginResponse(String accessToken, String refreshToken, boolean isAdmin) {
}
