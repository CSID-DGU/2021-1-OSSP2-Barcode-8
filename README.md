# Barcode

## 편의점 PB 상품 후기 공유 안드로이드 어플리케이션

![circleci](https://img.shields.io/circleci/build/github/CSID-DGU/2021-1-OSSP2-Barcode-8) ![last_commit](https://img.shields.io/github/last-commit/CSID-DGU/2021-1-OSSP2-Barcode-8) ![lang](https://img.shields.io/github/languages/top/CSID-DGU/2021-1-OSSP2-Barcode-8) ![issues](https://img.shields.io/github/issues/CSID-DGU/2021-1-OSSP2-Barcode-8)

### 개요

COVID-19시대를 겪으면서 우리 사회에 '집콕'과 '혼술'이 새로운 트렌드로 자리 잡았다. 이를 뒷받침하듯이 대한민국에서 가장 유명한 편의점 3사 (CU, GS, 세븐일레븐) 브랜드 공식 모바일 어플리케이션의 이용, 설치자 수가 전년 대비 모두 상승했다. 그러나 우리 팀이 주목한 부분은 편의점 소비가 증가한 것에 비해, 편의점에 존재하는 수 많은 PB (Private Brand) 상품들에 대한 정보가 부족해 소비자들이 좋은 선택을 할 수 있는 경우가 그리 많지 않다는 점이다.

따라서, 8조 Barcode 팀은 **'편의점 PB 상품들을 평가할 수 있는 모바일 어플리케이션'** 을 개발하여 소비자들의 합리적인 소비 및 기업 차원에서 더욱 질 좋은 PB 상품을 개발할 수 있도록 하는 Win-Win 전략을 취하려 한다.

### 스크린샷

<div display="block" style="overflow:scroll">
    <img width="200" src="https://user-images.githubusercontent.com/13748138/118665377-39576500-b82d-11eb-96fd-87f9ed59b94d.png">
    <img width="200" src="https://user-images.githubusercontent.com/13748138/122599850-f1d12c80-d0a9-11eb-97d0-1d8057de6d66.png">
    <img width="200" src="https://user-images.githubusercontent.com/13748138/118665404-3f4d4600-b82d-11eb-941a-36c2f2606e14.png">
<img width="200" src="https://user-images.githubusercontent.com/13748138/122599506-71123080-d0a9-11eb-80ec-f2b8f4e16c28.jpg">
<img width="200" src="https://user-images.githubusercontent.com/13748138/122599504-6fe10380-d0a9-11eb-8912-23ac0e19b7b7.jpg">
<img width="200" src="https://user-images.githubusercontent.com/13748138/122599488-6c4d7c80-d0a9-11eb-9d39-1ef6c8a390bb.jpg">
<img width="200" src="https://user-images.githubusercontent.com/13748138/122599496-6e174000-d0a9-11eb-9e9a-f40343557bd5.jpg">
<img width="200" src="https://user-images.githubusercontent.com/13748138/122599912-08778380-d0aa-11eb-87cb-34c5f7102bde.png">
<img width="200" src="https://user-images.githubusercontent.com/13748138/122599920-0ad9dd80-d0aa-11eb-8fa7-7835024041bf.png">
  </div>

### 영상

<iframe width="560" height="315" src="https://www.youtube.com/embed/1yLMYEP__5Y" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

<iframe width="560" height="315" src="https://www.youtube.com/embed/5OsS-53eLKo" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

### 라이선스

Barcode 프로젝트는 [Apache License 2.0](https://github.com/CSID-DGU/2021-1-OSSP2-Barcode-8/blob/main/LICENSE)을 따릅니다.

### 사용한 라이브러리

```{.gradle}
dependencies {
    implementation 'com.kakao.sdk:usermgmt:1.27.0'
    implementation "com.kakao.sdk:v2-user:2.5.0"
    implementation 'gun0912.ted:tedpermission:2.2.3'
    implementation 'com.google.android.gms:play-services-vision:20.1.3'
    implementation platform('com.google.firebase:firebase-bom:27.0.0')
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'com.google.firebase:firebase-core:18.0.3'
    implementation 'com.google.firebase:firebase-auth:20.0.4'
    implementation 'com.firebaseui:firebase-ui-auth:6.4.0'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0' 
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation 'androidx.navigation:navigation-fragment:2.3.5'
    implementation 'androidx.navigation:navigation-ui:2.3.5'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
}
```

### 팀원

- 팀장: 동국대학교 컴퓨터공학과 19학번 [조양진](https://github.com/RieLCho)
- 팀원: 동국대학교 컴퓨터공학과 16학번 [정동구](https://github.com/dsaf2007)
- 팀원: 동국대학교 컴퓨터공학과 17학번 [윤대현](https://github.com/eogus0512)
- 팀원: 동국대학교 컴퓨터공학과 17학번 [이주영](https://github.com/JuYeong98)
- 팀원: 동국대학교 컴퓨터공학과 18학번 [서지민](https://github.com/fruity1220)

See also the list of [contributors](https://github.com/CSID-DGU/2021-1-OSSP2-Barcode-8/graphs/contributors) who participated in this project.
