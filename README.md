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

- 이벤트 및 추첨 등으로 일부 사용자들에게 해당하는 이용권을 지급하기 위한 데이터

예약 알림

- 예약 시간이 다가온 (10분 전) 알림 전송을 위한 데이터

## ERD

![ERD](/docs/erd2.png)