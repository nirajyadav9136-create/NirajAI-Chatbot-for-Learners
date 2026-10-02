from __future__ import annotations

from .schemas import (
    AnswerEvaluationRequest,
    AnswerEvaluationResponse,
    DifficultyPredictionRequest,
    DifficultyPredictionResponse,
    SkillPredictionRequest,
    SkillPredictionResponse,
    TopicRecommendation,
    TopicRecommendationRequest,
    TopicRecommendationResponse,
)


def predict_skill(request: SkillPredictionRequest, model_version: str) -> SkillPredictionResponse:
    if request.questions_correct > request.questions_attempted:
        raise ValueError("questions_correct cannot exceed questions_attempted")
    score = 0.0 if request.questions_attempted == 0 else request.questions_correct / request.questions_attempted * 100
    return SkillPredictionResponse(skill_score=round(score, 2), model_version=model_version)


def predict_difficulty(request: DifficultyPredictionRequest, model_version: str) -> DifficultyPredictionResponse:
    if request.skill_score < 40 or request.recent_accuracy < 0.5:
        difficulty = "BEGINNER"
    elif request.skill_score < 75 or request.recent_accuracy < 0.8:
        difficulty = "INTERMEDIATE"
    else:
        difficulty = "ADVANCED"
    return DifficultyPredictionResponse(difficulty=difficulty, confidence=0.6, model_version=model_version)


def recommend_topics(request: TopicRecommendationRequest, model_version: str) -> TopicRecommendationResponse:
    recommendations = sorted(request.topic_scores, key=lambda score: score.skill_score)[: request.limit]
    return TopicRecommendationResponse(
        recommendations=[
            TopicRecommendation(
                topic_id=item.topic_id,
                reason="Build confidence with fundamentals and targeted practice.",
                priority=1 if item.skill_score < 50 else 2,
            )
            for item in recommendations
        ],
        model_version=model_version,
    )


def evaluate_answer(request: AnswerEvaluationRequest, model_version: str) -> AnswerEvaluationResponse:
    if request.question_type in {"MCQ", "TRUE_FALSE"}:
        correct = request.submitted_answer.strip().casefold() == request.expected_answer.strip().casefold()
        return AnswerEvaluationResponse(
            correct=correct,
            score=100.0 if correct else 0.0,
            feedback="Correct answer." if correct else "Review the concept and try again.",
            model_version=model_version,
        )
    return AnswerEvaluationResponse(
        correct=False,
        score=0.0,
        feedback="This answer type requires the configured evaluation model.",
        model_version=model_version,
    )
