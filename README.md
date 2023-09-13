# 이용권 관리 서비스

> 헬스장 이용권 관리 서비스 프로젝트

# 프로젝트 구성 항목

- spring boot 2.7.x
- java 17
- spring batch
- mariaDB ,h2
- spring data jpa

# 요구 사항

- 이용자는 N개의 이용권 소지 가능
- 이용권은 횟수가 모두 소진되거나 기한이 만료 시 소멸
- 이용권 만료 전 사용자에게 알림 전송
- 이용권 제공 업체에서 원하는 시간을 설정하여 일괄로 이용자들에게 이용권을 지급 기능
- 예약된 수업 10분 전 알림
- 수업 종료 시점 수업을 예약한 이용자들의 이용권 횟수 자동 차감 기능
- 이용자의 이용 현황 등에 대한 통계 데이터 제공

# 가이드

## 배치 처리 서버 API docs

[배치 프로세스 서버 API docs](https://port-0-ticket-manager-cgw1f2almgb9af4.sel5.cloudtype.app/swagger-ui/index.html)

### 파라미터 필요 요소 요청 예시

```json
{
    "jobName" : "initReservationHistoryJob",
    "jobProperties" : {
        **"from" : "2023-09-01",
        "to" : "2023-09-03"**
    }
}
```

- jobParameter 내 from, to 속성 값으로 일정 시간대에 예약 데이터를 이력 데이터화 하여 저장한다.

## 웹페이지

[헬스장 이용권 관리 페이지](https://port-0-ticket-manager-cgw1f2almghf1sz.sel5.cloudtype.app/)

해당 URL을 통해 이용권 관리용 페이지 접근 가능

### 주요 메뉴

[이용권 예약 현황]

- 저장된 이용권 목록 조회

[이용권 발급]

- 사용자 그룹에 따른 무료 이용권 발급 요청 처리
- 발급 요청 대기중인 무료 이용권 목록 조회

[통계]

- 일자별 예약 수, 취소 수 조회

# 데이터 설계

![DataArichtecture](/docs/ticket-manager-erd.png)

사용자

- 이용권 구매 및 사용할 회원 데이터

패키지

- 이용권을 사용하는 서비스 데이터

이용권

- 패키지에서 사용에 필요한 데이터 및 현 프로젝트에서 주로 다룰 데이터

예약

- 이용권을 사용하여 패키지 예약과 관련된 데이터

사용자 그룹

- 특정 목적 하에 여러 사용자를 묶은 데이터

무료 이용권

- 이벤트 및 추첨 등으로 일부 사용자들에게 해당하는 패키지에 대한 이용권을 지급하기 위한 데이터

예약 알림

- 예약 시간이 다가온 (10분 전) 알림 전송을 위한 데이터

이용 현황

- 일자 별 이용 현황 저장용 데이터

## ERD

![ERD](/docs/erd2.png)