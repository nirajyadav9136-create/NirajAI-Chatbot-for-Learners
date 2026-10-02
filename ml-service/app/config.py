from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    model_version: str = "baseline-v1"
    model_config = SettingsConfigDict(env_file=".env", extra="ignore")


settings = Settings()
