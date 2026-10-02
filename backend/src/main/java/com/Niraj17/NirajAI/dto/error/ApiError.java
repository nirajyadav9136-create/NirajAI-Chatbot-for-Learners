package com.Niraj17.NirajAI.dto.error;

import java.time.Instant;

public record ApiError(boolean success, String message, String errorCode, Instant timestamp, String path) {
}
