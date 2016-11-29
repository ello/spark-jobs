import co.ello.testing._
import co.ello.impressions._
import java.nio.file.{Files, Paths}
import com.holdenkarau.spark.testing.{SharedSparkContext, RDDComparisons}

class FileSnapshotLoaderSpec extends UnitSpec with Redis with SharedSparkContext with RDDComparisons {

  describe("Loading counts from a flat file") {
    it("returns an RDD of id/count pairs") {
      val snapshotPath = Paths.get(getClass.getResource("/fixtures/snapshot/").toURI).toString
      val snapshot = FileSnapshotLoader(sc, snapshotPath).collect()

      snapshot should contain (("1", 4L))
      snapshot should contain (("2", 2L))
    }
  }

  describe("Loading counts from an empty directory") {
    it("returns an empty RDD") {
      val snapshotPath = Paths.get(getClass.getResource("/fixtures/empty_snapshot/").toURI).toString
      val snapshot = FileSnapshotLoader(sc, snapshotPath).collect()

      snapshot shouldBe empty
    }
  }
}