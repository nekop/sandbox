package org.infinispan.stress;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.test.MultipleCacheManagersTest;
import org.infinispan.test.fwk.TestCacheManagerFactory;
import org.infinispan.util.concurrent.IsolationLevel;
import org.infinispan.transaction.TransactionMode;
import org.testng.annotations.Test;
import org.infinispan.test.fwk.CleanupAfterMethod;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;

import static org.testng.AssertJUnit.*;

/**
 * @author Takayoshi Kimura
 * @since 7.0
 */
@Test(testName = "stress.DistReplaceStressTest", groups = "stress")
@CleanupAfterMethod
public class DistReplaceStressTest extends MultipleCacheManagersTest {

   private static final int THREADS_NUM = 40;
   private static final int LOOP_PER_THREAD = 10 * 1000;
   private static final String KEY = "key";

   @Override
   protected void createCacheManagers() throws Throwable {
      ConfigurationBuilder builder = TestCacheManagerFactory.getDefaultCacheConfiguration(true);

      builder
            .clustering()
            .cacheMode(CacheMode.DIST_SYNC)
            .locking()
            .isolationLevel(IsolationLevel.READ_COMMITTED)
            .transaction()
            .transactionMode(TransactionMode.NON_TRANSACTIONAL);

      createCluster(builder, 2);
      waitForClusterToForm();
   }

   public void testReplace() throws Exception {
      final Cache<String, Integer> c1 = cache(0);
      c1.put(KEY, 0);
      CountDownLatch startLatch = new CountDownLatch(1);
      AtomicInteger replaced = new AtomicInteger();
      ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);
      for (int i = 0; i < THREADS_NUM; i++) {
         ReplaceTask task = new ReplaceTask(startLatch, replaced, c1);
         executor.submit(task);
      }
      startLatch.countDown();
      executor.shutdown();
      if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
         executor.shutdownNow();
      }
      System.out.println(c1.get(KEY));
      System.out.println(replaced);
      assert c1.get(KEY) == replaced.intValue();
   }

   class ReplaceTask implements Runnable {
      CountDownLatch startLatch;
      AtomicInteger replaced;
      Cache<String, Integer> cache;
      public ReplaceTask(CountDownLatch startLatch,
                         AtomicInteger replaced,
                         Cache<String, Integer> cache) {
         this.startLatch = startLatch;
         this.replaced = replaced;
         this.cache = cache;
      }
      public void run() {
         try {
            startLatch.await();
         } catch (InterruptedException ignore) { }
         for (int i = 0; i < LOOP_PER_THREAD; i++) {
            int oldVal = cache.get(KEY);
            int newVal = oldVal + 1;
            boolean success = cache.replace(KEY, oldVal, newVal);
            if (success) {
               replaced.incrementAndGet();
            }
         }
      }
   }

}
