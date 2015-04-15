import twitter4j._
import conf._
import scala.util.Random
 
object Main {
  var randomNumber:Int = 0
  def generateRandomInt() = {
    val r = new Random
    this.randomNumber = r.nextInt(100)
  }
  def tweetUser(text: String, user: String) = {
    this.tweet("@"+user+" "+text)
  }
  def tweet(text:String) = {
    val twitter = TwitterFactory.getSingleton()
    val status = twitter.updateStatus(text)
  }
  def main(args: Array[String]) = {
    this.generateRandomInt()
    println(this.randomNumber);

    val that = this;
    val listener: StatusListener = new StatusListener {
      def onStatus(status: Status) = {
        //println(status.getUser.getName + " : " + status.getText)

        val text = status.getText
        val screenName = status.getUser.getScreenName

        val num = try {
          text.split(" ").last.toInt
        }
        catch {
          case e:Exception => -1
        }

        if(num == -1){
          println("数字にしてー＞＜");
        }
        else if(num < that.randomNumber){
          println("小さいよー")
          that.tweetUser("小さいよ―", screenName)
        }
        else if(num > that.randomNumber){
          println("大きいよ―")
          that.tweetUser("大きいよ―", screenName)
        }
        else {
          println("正解！！")
          that.generateRandomInt()
          that.tweetUser("正解！！", screenName)
          that.tweet("数字が新しくなりました！！")
        }

      }
      def onDeletionNotice(s: StatusDeletionNotice) = {}
      def onStallWarning(s: StallWarning) = {}
      def onTrackLimitationNotice(numberOfLimitedStatuses: Int) = {}
      def onException(ex: Exception) = ex.printStackTrace()
      def onScrubGeo(userId: Long, upToStatusId: Long) = {}
    }

    val twitterStream = new TwitterStreamFactory().getInstance
    twitterStream.addListener(listener)
    twitterStream.user()
  }
}
