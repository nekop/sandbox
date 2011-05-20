def executor = java.util.concurrent.Executors.newCachedThreadPool();

c = {
  def count = 0;
  while (count != 1000) {
      count++;
      System.out.println("""${Thread.currentThread().getName()} === ${count}""");
  }
}
for (int i = 0; i < 2; i++) {
  executor.submit(c);
}
executor.shutdown();




