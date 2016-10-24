import org.scalatest.{FunSpec, Matchers, BeforeAndAfter}
import java.nio.charset.StandardCharsets._
import java.nio.file.{Files, Paths}
import co.ello.impressions.PostWasViewedDecoder

class PostWasViewedDecoderSpec extends FunSpec with Matchers with BeforeAndAfter {

  class AvroFixture(path: String) {
    val avroBytes = Files.readAllBytes(Paths.get(getClass.getResource(s"/fixtures/$path.avro").toURI))
  }

  describe("Decoding a valid Avro record with all fields") {
    it("returns a tuple of (post_id, author_id, viewer_id)") {
      new AvroFixture("post_was_viewed") {
        PostWasViewedDecoder(avroBytes) shouldEqual Seq(("11", "1", "1"))
      }
    }
  }

  describe("Decoding a valid Avro record with no viewer") {
    it("returns a tuple of (post_id, author_id, null)") {
      new AvroFixture("post_was_viewed_null_viewer") {
        PostWasViewedDecoder(avroBytes) shouldEqual Seq(("11", "1", null))
      }
    }
  }

  describe("Decoding a valid Avro record with the wrong event name") {
    it("returns an empty Seq") {
      new AvroFixture("post_was_loved") {
        PostWasViewedDecoder(avroBytes) shouldEqual Seq()
      }
    }
  }
}
