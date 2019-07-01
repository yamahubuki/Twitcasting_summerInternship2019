# Twitcasting_summerInternship2019

## 概要

- [ツイキャス](https://twitcasting.tv/)を運営する[モイ株式会社](https://about.moi.st/ja/)の2019年度サマーインターンシップの応募課題
- チャレンジにはレベル１～３があるが、ここではレベル３のみを取り扱う。
- 符号部分が隠された数式とその計算結果が与えられ、隠された符号を回答する。
- チャレンジはHTTPを用いてAPIとの通信により行い、正解までに行った誤答数と正解までにかかる時間を競う。
- 課題の詳細およびランキングは[募集ページ](https://twitcasting.tv/internship2019.php)にて公開されている。

## 動作確認環境

- Windows７・10
- javac version 1.8.0_111
- Java Version1.8.0_191

## ３つのプログラム

### Challenge1

- jacksonのJSONライブラリを使用
- ３回間違え、その際のヒントを基に４回目で確実に正解する事を目指した

### Challenge2

- JSONライブラリの利用を辞めるなど、処理の高速化を図った。

### Challenge3

- 誤答なしでの正解をめざし、内部で総当たりによる符号の割り出しを実施
- アクセス修飾子privateやfinalの積極的な指定など、さらなる高速化を実施


### 使用にあたって

- 各プログラム冒頭にある文字列型変数tokenに自身のトークンを入れてからコンパイルして利用すること。
- 作者は動作保証をしない。利用者自身の責任において利用すること。


### おまけ

- test.javaは、Challenge3の作成途中で使用したテストプログラム
- 406回のループで符号の組み合わせを網羅していることの確認が行える。


### ランキング情報

- 誤答による減点はとても大きい。
- 通信が多少遅くても、プログラムに無駄があっても、誤答なしなら150,000点を超えそう。
- 数式網羅が難しいなら、+と-のみで解凍できる問題が来るまで回し続けるなども一つの手段。
- 結局、ランキング上位に入るためには問題の運はとても大きい。
- 通信速度による差は、誤答なし(＝通信２回)の場合で前後2,000点程度ではないかと思われる。
- これらは何の根拠もない作者の主観である。

