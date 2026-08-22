# WorkTimeManager

Spring Bootで作成した勤怠管理アプリケーションです。
従業員の出勤・退勤・休憩を記録し、管理者画面から当日の勤怠状況を確認できます。

## 主な機能

- ログイン機能
- 管理者画面
  - 当日の出勤者数の確認
  - 休憩中の人数の確認
  - 退勤済み人数の確認
  - 従業員一覧の表示
- 従業員画面
  - 出勤登録
  - 退勤登録
  - 休憩開始
  - 休憩終了
  - 出勤中ユーザーの一覧表示
  
##　使用技術
  
- Java 21
- Spring Boot 4.0.7
- Spring MVC
- Spring Security
- Thymeleaf
- MyBatis
- MySQL
- Bootstrap 5
- Lombok
- Maven

### DB接続設定
src/main/resources/application.propertiesの接続情報を自分の環境に合わせて変更します。

### アプリケーションを起動
./mvnw spring-boot:run

起動後、以下のURLにアクセスします。
http://localhost:8080/

### テーブル構成
- common_login : ログインユーザー情報
- worker : 従業員情報
- worker_time : 出勤・退勤情報
- break_time : 休憩開始・終了情報

## サンプルログイン情報

### 管理者

| ユーザーID | パスワード |

|   admin   | admin123 |

### 従業員

| ユーザーID | パスワード |

|  worker  | worker123 |

### 勤怠用サンプル従業員

| 社員番号 | 氏名 | パスワード |

| worker01 | 山田太郎 | password |

| worker02 | 田中太郎 | password |

| worker03 | 佐藤太郎 | password |

### 工夫した点
- 管理者画面と従業員画面を分けて、役割ごとに使いやすい画面構成にしました。
- 出勤、退勤、休憩開始、休憩終了をボタン操作で登録できるようにしました。
- MyBatisを使用して、SQLとJavaの処理を分けて管理しやすくしました。
- Thymeleafを使用して、画面表示をサーバー側で動的に切り替えられるようにしました。

### 今後の改善点
- パスワードを暗号化して保存する
- 管理者だけが管理者画面を操作できるように認可処理を強化する
- 月ごとの勤怠一覧や勤務時間の集計機能を追加する
- 従業員の登録・編集・削除機能を追加する