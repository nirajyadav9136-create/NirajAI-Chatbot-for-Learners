from __future__ import annotations

from typing import Literal
from uuid import UUID

from pydantic import BaseModel, Field


class SkillPredictionRequest(BaseModel):
    user_id: UUID
    topic_id: UUID
    questions_attempted: int = Field(ge=0)
    questions_correct: int = Field(ge=0)
    time_spent_minutes: int = Field(ge=0)


class SkillPredictionResponse(BaseModel):
    skill_score: float = Field(ge=0, le=100)
    model_version: str


class DifficultyPredictionRequest(BaseModel):
    skill_score: float = Field(ge=0, le=100)
    recent_accuracy: float = Field(ge=0, le=1)


class DifficultyPredictionResponse(BaseModel):
    difficulty: Literal["BEGINNER", "INTERMEDIATE", "ADVANCED"]
    confidence: float = Field(ge=0, le=1)
    model_version: str


class TopicScore(BaseModel):
    topic_id: UUID
    skill_score: float = Field(ge=0, le=100)


class TopicRecommendationRequest(BaseModel):
    user_id: UUID
    topic_scores: list[TopicScore] = Field(default_factory=list)
    limit: int = Field(default=5, ge=1, le=20)


class TopicRecommendation(BaseModel):
    topic_id: UUID
    reason: str
    priority: int = Field(ge=1)


class TopicRecommendationResponse(BaseModel):
    recommendations: list[TopicRecommendation]
    model_version: str


class AnswerEvaluationRequest(BaseModel):
    question_type: Literal["MCQ", "TRUE_FALSE", "SHORT_ANSWER", "CODE_OUTPUT", "CODE_DEBUGGING", "CODING"]
    submitted_answer: str = Field(max_length=20_000)
    expected_answer: str = Field(max_length=20_000)


class AnswerEvaluationResponse(BaseModel):
    correct: bool
    score: float = Field(ge=0, le=100)
    feedback: str
    model_version: str
