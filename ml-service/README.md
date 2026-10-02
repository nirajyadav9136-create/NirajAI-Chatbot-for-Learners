# DevTutor AI ML Service

Independent Python FastAPI service for machine-learning functionality. Spring Boot communicates with this service over HTTP and remains usable when the service is unavailable.

## Run locally

```powershell
python -m venv .venv
.\.venv\Scripts\Activate.ps1
pip install -r requirements.txt
uvicorn app.main:app --reload --port 8000
```

Run tests with:

```powershell
pytest
```

Set `ML_SERVICE_URL=http://localhost:8000` in the backend environment to enable the Spring Boot client.

The current implementation is a deterministic baseline contract. Replace functions in `app/baseline.py` with trained-model adapters as notebooks and evaluation work mature.
