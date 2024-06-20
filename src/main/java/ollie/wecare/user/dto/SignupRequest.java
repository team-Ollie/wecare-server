package ollie.wecare.user.dto;

public record SignupRequest(String loginId,
                            String password,
                            String nickname,
                            String identifier,
                            Long centerIdx) {}
