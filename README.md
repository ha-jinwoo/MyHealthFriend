<p align="center">
  <img src="https://github.com/ha-jinwoo/CapStone-APP-ResultVideo/blob/master/Capstoneresult/app/src/main/res/mipmap-xxxhdpi/ic_launcher_foreground.png" />
</p>

----------------

## 프로젝트 개발배경

최근 운동에 관심 있는 사람들이 증가하여 운동 인구 및 시장이 커져가고 있습니다.
실제로 통계치를 살펴보면 2000년도엔 30%에 불과했던 운동인구가 2019년도엔 52.2%로 증가한 것을 확인할 수 있습니다. 또한 운동을 배우고 싶어 홈트 영상을 찾아보는 사람도 늘었습니다. 구독자가 10만 명 이상인 헬스 유튜버의 통계치를 보면, 2015년도엔 15명에 불과했지만, 2020년도엔 3배 이상 증가한 48명인 것을 확인할 수 있습니다. 그만큼 운동에 관한 국민 관심이 증가했다는 말입니다.

하지만 현실적인 어려움 때문에 운동을 하지 못하는 사람들이 있습니다. 월 7만원에 이르는 헬스장 이용 금액, 1회 10만원 정도 하는 PT 비용, 직장생활, 육아 등으로 인한 여가 시간의 부족, 코로나로 인한 사회적 거리두기, “내가 운동을 할 수 있을까?”, “운동 하다가 다치면 어떡하지” 등과 같은 두려움 등이 운동을 시작하는데 있어서 큰 방해요소로 작용합니다.

그래서 저희는 굳이 헬스장에 가지 않아도 사용자의 자세를 분석하여 자세 교정을 해주는 AI 홈트레이너 어플 __“나의 운동친구”__ 를 만들게 되었습니다.

## 앱 소개

언제, 어디서든 사용할 수 있는 AI 홈트레이너라는 장점을 부각시키기 위해 접근성이 좋은 모바일 앱으로 제작하였습니다. 먼저 사용자가 원하는 운동을 클릭하면 올바른 동작을 사용자에게 가르쳐 주기 위한 튜토리얼 영상을 실행합니다. 튜토리얼 영상을 시청하고 나면 사용자가 직접 운동을 시작하게 됩니다. 사용자 몸의 각 관절을 인식하여 __올바른 자세인지 잘못된 자세인지 판별__ 을 합니다. 만약 잘못 된 자세인 경우 “허리 숙이지마”와 같이 실시간 __음성 피드백__ 을 해줍니다. 이로 인해 사용자는 내가 운동을 잘 하고 있는지 알 수 있고 잘못된 자세로 인해 몸이 다칠 가능성 또한 줄일 수 있습니다. 추가적으로 음성 피드백으로 인해 별도의 조작 없이 운동에 집중할 수 있습니다. 이렇게 운동을 하면 사용자가 어떤 운동을 얼마나 했는지에 대한 데이터가 쌓이게 됩니다. 이 정보를 바탕으로 데이터를 시각화 시켜줘 자신이 한 운동량을 한 눈에 확인할 수 있습니다.

<p align="center">
  <img src="https://github.com/ha-jinwoo/hufs/blob/master/readme_image/%EC%8B%A4%ED%96%89%EC%98%81%EC%83%81.gif" />
</p>

## 타 앱과의 차별성

Like Fit이라는 어플은 자세에 대한 피드백 없이 단순히 운동 횟수의 카운트 만을 해주어 트레이너라고 보긴 어렵습니다. 그에 반해 “나의 운동 친구”는 사용자의 자세를 분석하여 잘못된 자세를 인식합니다. 그 후 음성 피드백을 통해 자세를 교정 해주어 트레이너의 역할을 수행 합니다.

또 다른 어플로 모두의 트레이닝이 있습니다. 모두의 트레이닝은 운동을 어떻게 하는지에 대한 영상만을 제공해줍니다. 이는 사용자가 올바른 자세로 운동을 하고 있는지 알 수 없어 부상으로 이어 질 수 있습니다. 그에 반해 나의 운동친구는 일방적으로 영상을 강요하지 않고 올바른 자세로 운동을 시켜주어 운동에 대한 자신감과 부상 방지를 해줍니다.

## OPENAPI 사용

첫 번째로 사용한 API는 __OpenPose__ 입니다. OpenPose는 사전에 학습 시켜 놓은 tf lite모델을 통해 사람의 관절을 위치 별로 분류합니다. 저희는 이 분류 된 관절의 위치를 통해 자세 교정을 시행하는 알고리즘을 제작하였습니다.

두 번째로 사용한 API는 __MP안드로이드 차트__ 입니다. MP안드로이드 차트는 입력 받은 값을 그래프를 통해 시각화 시켜주는 API입니다. 이를 통해 사용자가 소모한 칼로리, 운동 시간을 DB에서 받아와 시각화 시켜줍니다. 

세 번째로 사용한 API는 __유튜브 플레이어 뷰__ 입니다. 유튜브 플레이어 뷰는 유튜브 영상의 ID를 입력 받아 해당 영상을 어플내에서 실행 시켜주는 API입니다. 이를 통해 사용자에게 튜토리얼 영상을 제공하고 올바른 운동의 지식을 부여해 줍니다.
