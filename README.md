```
### Environment
`local` 프로필로 동작할 수 있도록, 필요 인프라를 `docker-compose` 로 제공합니다.
```shell
docker-compose -f ./docker/infra-compose.yml up
```
### Monitoring
`local` 환경에서 모니터링을 할 수 있도록, `docker-compose` 를 통해 `prometheus` 와 `grafana` 를 제공합니다.

애플리케이션 실행 이후, **http://localhost:3000** 로 접속해, admin/admin 계정으로 로그인하여 확인하실 수 있습니다.
```shell
docker-compose -f ./docker/monitoring-compose.yml up
```



### 결제 요청
POST {{pg-simulator}}/api/v1/payments
X-USER-ID: 135135
Content-Type: application/json

{
  "orderId": "1351039135",
  "cardType": "SAMSUNG",
  "cardNo": "1234-5678-9814-1451",
  "amount" : "5000",
  "callbackUrl": "http://localhost:8080/api/v1/examples/callback"
}

### 결제 정보 확인
GET {{pg-simulator}}/api/v1/payments/20250816:TR:9577c5
X-USER-ID: 135135

### 주문에 엮인 결제 정보 조회
GET {{pg-simulator}}/api/v1/payments?orderId=1351039135
X-USER-ID: 135135


- 결제 수단으로 PG 기반 카드 결제 기능을 추가합니다.
- PG 시스템은 로컬에서 실행가능한 `pg-simulator` 모듈이 제공됩니다. ( 별도 SpringBootApp )
- PG 시스템은 **비동기 결제** 기능을 제공합니다.

> *비동기 결제란, 요청과 실제 처리가 분리되어 있음을 의미합니다.*
**요청 성공 확률 : 60%
요청 지연 :** 100ms ~ 500ms
**처리 지연** : 1s ~ 5s
**처리 결과**
* 성공 : 70%
* 한도 초과 : 20%
* 잘못된 카드 : 10%
>