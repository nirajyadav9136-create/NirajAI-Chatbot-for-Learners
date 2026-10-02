from uuid import uuid4

from fastapi.testclient import TestClient

from app.main import app

client = TestClient(app)


def test_health():
    response = client.get("/health")
    assert response.status_code == 200
    assert response.json()["status"] == "ok"


def test_skill_prediction():
    response = client.post(
        "/predict/skill",
        json={
            "user_id": str(uuid4()),
            "topic_id": str(uuid4()),
            "questions_attempted": 4,
            "questions_correct": 3,
            "time_spent_minutes": 20,
        },
    )
    assert response.status_code == 200
    assert response.json()["skill_score"] == 75.0


def test_answer_evaluation_is_exact_for_mcq():
    response = client.post(
        "/evaluate/answer",
        json={
            "question_type": "MCQ",
            "submitted_answer": " Class ",
            "expected_answer": "class",
        },
    )
    assert response.status_code == 200
    assert response.json()["correct"] is True
