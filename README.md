# tribunal-framework

# 概要
tribunal-frameworkは「プラグイン」と「ルール」で簡単に拡張する事が可能なチャットボットフレームワークです。

# 特徴
- チャットボットの開発に必要な「文章を解析する機能」が実装済み。XML形式のファイルに解析したい文章にマッチする正規表現を「ルール」として記述していくだけ。

- あなたが書くのは「どんな文章を解析させたいか(ルール)」と「どんな処理をさせたいか(プラグイン)」の2つだけ。

- プログラムの修正はプラグイン(Jarファイル)単位もしくはルールの変更をするだけ。

# 構造
  - /plugins ... ここに入れたJarファイルをプラグインとして自動登録する
  - /rules ... ここに入れたXMLファイルをルールとして自動登録する
  - tribunal-core
    - PluginService ... 全てのJarのPluginManagerを管理する
    - PluginManager ... 単一のJarのPluginを管理する
    - Plugin ... 処理本体
    - RuleService ... 全てのJarのルールを管理する
    - RuleManager ... 単一のJarのルールを管理する
    - Rule ... ルール管理オブジェクト
  - tribunal-tool ... デバッグ用ロガーやJarスキャナを内包
  - tribunal-application ... デモアプリケーションプロジェクト
  - tribunal-test ... デモ用プラグインプロジェクト

# フロー
1. PluginService初期化時に各Jar内のPluginManagerを探して登録。  
PluginManager登録時にJar内の各Pluginが初期化される。  

2. RuleService初期化時に各Jar内のRuleManagerを探して登録。  
RuleManager登録時にルール(XMLファイル)を読み込む。  

3. PluginServiceを開始するとPluginManagerに記述された任意の処理が実行される。  

4. ルールにマッチする文章が与えられた時、任意のPluginが実行される。

# 実行
```bash
./gradlew build
./gradlew :tribunal-application:run
```
