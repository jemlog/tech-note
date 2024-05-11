# Envoy Proxy & Istio

## Istio의 등장 배경

서비스 메시는 복잡한 MSA에서 발생 가능한 문제들을 개발 프레임워크 단에서 통제하는 것이 아닌 **인프라에서 관리**하기 위한 아키텍처 컨셉이다.

예를 들어 자바에서는 서비스 간 장애 전파 차단을 Resilence4j라는 라이브러리를 통해서 해결했다면, 서비스 메시는 Envoy Proxy를 사용해서 어플리케이션과 독립적으로 Circuit Breaker 패턴 적용이 가능하다.

Envoy Proxy의 경우, 사이드카 형태로 모든 파드마다 Sidecar Injection이 되서 배포된다. 만약 파드 개수가 무수히 많아진다면 개별 Envoy Proxy들의 설정값을 관리하기 어려워진다. 이를 해결하기 위해 등장한 것이
중앙 통제 용도의 **컨트롤 플레인**이고 Istio가 이 역할을 한다.


<img width="700" src="https://github.com/CMC11th-Melly/Melly_Server/assets/82302520/0ae3f423-02fd-46e0-bc84-fc7903207798" alt="My Image">


## Istio의 구조

### Pilot

- envoy에 대한 설정을 관리한다. 서비스들의 엔드포인트들의 주소를 얻을 수 있는 서비스 디스커버리 역할을 한다.
- 서비스 안정성을 위한 retry, circuit breaker, timeout 등의 기능 제공

### Mixer
- 액세스 컨트롤, 정책 통제, 모니터링 지표 수집 등의 기능을 수행

### Citadel
- 서비스를 사용하기 위한 사용자 인증과 인가 담당. TLS 암호화나 사용자 인증에 필요한 인증서를 관리하는 역할

## Istio의 기능

### 트래픽 통제

- 카나리 배포가 가능하다. 새 기능 배포할때 뒷단 서비스에 95%는 기존 트래픽 전송하고 5%는 새로운 새 버전으로 전송하는 식으로 트래픽 분할 가능하다.
- User-Agent별로 라우팅하는 지능형 라우팅 가능하다

### 안정성 확보

- 대상 서비스가 여러 인스턴스로 구성되어 있으면 이를 로드밸런싱 하고 이 서비스들에 대한 주기적인 헬스체크를 진행한다. 만약 장애 서비스 있으면 자동으로 서비스에서 제거

### 보안

- Envoy Proxy를 통한 통신은 기본적으로 TLS 암호화가 들어간다. 암호화를 위한 인증서는 Citadel에 있는 인증서를 다운 받아서 사용한다.

### 모니터링

Mixer는 Envoy Proxy들 간의 요청 흐름을 모두 기록할 수 있다. 이를 모니터링 툴에서 분석 가능.