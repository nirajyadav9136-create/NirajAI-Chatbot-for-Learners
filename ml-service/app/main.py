from fastapi import FastAPI, HTTPException

from .baseline import evaluate_answer, predict_difficulty, predict_skill, recommend_topics
from .config import settings
from .schemas import (
    AnswerEvaluationRequest,
    AnswerEvaluationResponse,
    DifficultyPredictionRequest,
    DifficultyPredictionResponse,
    SkillPredictionRequest,
    SkillPredictionResponse,
    TopicRecommendationRequest,
    TopicRecommendationResponse,
)

app = FastAPI(title="DevTutor AI ML Service", version="0.1.0")


@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "ok", "model_version": settings.model_version}


@app.post("/predict/skill", response_model=SkillPredictionResponse)
def skill_prediction(request: SkillPredictionRequest) -> SkillPredictionResponse:
    try:
        return predict_skill(request, settings.model_version)
    except ValueError as error:
        raise HTTPException(status_code=400, detail=str(error)) from error


@app.post("/predict/difficulty", response_model=DifficultyPredictionResponse)
def difficulty_prediction(request: DifficultyPredictionRequest) -> DifficultyPredictionResponse:
    return predict_difficulty(request, settings.model_version)


@app.post("/recommend/topic", response_model=TopicRecommendationResponse)
def topic_recommendation(request: TopicRecommendationRequest) -> TopicRecommendationResponse:
    return recommend_topics(request, settings.model_version)


@app.post("/evaluate/answer", response_model=AnswerEvaluationResponse)
def answer_evaluation(request: AnswerEvaluationRequest) -> AnswerEvaluationResponse:
    return evaluate_answer(request, settings.model_version)
