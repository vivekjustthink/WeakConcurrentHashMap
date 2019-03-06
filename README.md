# WeakConcurrentHashMap

A Weak Concurrent Hash Map Solution which stores the keys and values only for a specific amount of time, and then expires after that
  time.
  
  <pre>
  
  // Create a Map Object
  long expiryInMillis = 1  60  1000;	// 1 minute
  WeakConcurrentHashMap&lt;String, Object&gt; map = new WeakConcurrentHashMap&lt;String, Object&gt;(expiryInMillis);
  
  // Use it
  map.put(&quot;key&quot;, valueObject);
  Object valueObject = map.get(&quot;key&quot;);
  
  // quit using it
  map.quitMap();
  </pre>
  
  And to check if the map is alive
  
  <pre>
  if (map.isAlive()) {
  	// Your operations on map
  }
  </pre>
  
  @author Vivekananthan M (vivekjustthink@gmail.com)
