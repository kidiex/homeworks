import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
public class ClassicIOCacheWithLimit {
    private final Map<String, FileCacheEntry> cache;
    private final int maxSize;
    
    private static class FileCacheEntry {
        private final String content;
        private final long lastReadTime;
        private final long lastModifiedTimeAtRead;
        
        public FileCacheEntry(String content, long lastReadTime, long lastModifiedTimeAtRead) {
            this.content = content;
            this.lastReadTime = lastReadTime;
            this.lastModifiedTimeAtRead = lastModifiedTimeAtRead;
        }
        
        public String getContent() {
            return content;
        }
        
        public long getLastReadTime() {
            return lastReadTime;
        }
        
        public long getLastModifiedTimeAtRead() {
            return lastModifiedTimeAtRead;
        }
    }
    
    public ClassicIOCacheWithLimit(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Некоректная длина кеша");
        }
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<>(maxSize, 0.75f, true);
    }
    
    public ClassicIOCacheWithLimit() {
        this(100);
    }
    
    public String readFile(String filePath) throws IOException {
        File file = new File(filePath);
        
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        
        String absolutePath = file.getAbsolutePath();
        long currentModifiedTime = file.lastModified();
        long currentTime = System.currentTimeMillis();
        
        if (cache.containsKey(absolutePath)) {
            FileCacheEntry cachedEntry = cache.get(absolutePath);
            if (isCacheValid(cachedEntry, currentModifiedTime)) {
                cache.remove(absolutePath);
                cache.put(absolutePath, new FileCacheEntry(cachedEntry.getContent(), currentTime, cachedEntry.getLastModifiedTimeAtRead()));
                return cachedEntry.getContent();
            }
        }
        
        return updateCache(file, absolutePath, currentModifiedTime);
    }
    
    private boolean isCacheValid(FileCacheEntry cachedEntry, long currentModifiedTime) {
        return cachedEntry.getLastModifiedTimeAtRead() == currentModifiedTime;
    }
    
    private String updateCache(File file, String absolutePath, long currentModifiedTime) throws IOException {
        String content = readFileContent(file);
        long currentTime = System.currentTimeMillis();
        FileCacheEntry newEntry = new FileCacheEntry(content, currentTime, currentModifiedTime);
        if (cache.size() >= maxSize) {
            removeOldestEntry();
        }
        cache.put(absolutePath, newEntry);
        return content;
    }
    
    private void removeOldestEntry() {
        Iterator<Map.Entry<String, FileCacheEntry>> iterator = cache.entrySet().iterator();
        if (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }
    
    private String readFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        char[] buffer = new char[8192];
        
        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                content.append(buffer, 0, charsRead);
            }
        }
        return content.toString();
    }
    
    public void invalidate(String filePath) {
        File file = new File(filePath);
        String absolutePath = file.getAbsolutePath();
        cache.remove(absolutePath);
    }

    public void invalidateAll() {
        cache.clear();
    }
    
    public boolean isCached(String filePath) {
        File file = new File(filePath);
        String absolutePath = file.getAbsolutePath();
        return cache.containsKey(absolutePath);
    }
    
    public int getCachedFilesCount() {
        return cache.size();
    }
    
    public long getCacheSizeInMemory() {
        long totalSize = 0;
        for (FileCacheEntry entry : cache.values()) {
            totalSize += entry.getContent().length() * 2L;
        }
        return totalSize;
    }
    
    public void printCacheStats() {
        System.out.println("Статистика кеша");
        System.out.println("Количество файлов в кеше: " + getCachedFilesCount());
        System.out.println("Размер кеша в памяти: " + getCacheSizeInMemory() + " байт");
        System.out.println("Максимальный размер кеша: " + maxSize + " файлов");
        System.out.println("Закешированные файлы:");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map.Entry<String, FileCacheEntry> entry : cache.entrySet()) {
            String filePath = entry.getKey();
            FileCacheEntry cacheEntry = entry.getValue();
            long fileSize = cacheEntry.getContent().length() * 2L;
            String lastRead = sdf.format(new Date(cacheEntry.getLastReadTime()));
            System.out.printf("  - %s (Размер: %d байт, Последнее чтение: %s)%n", filePath, fileSize, lastRead);
        }
    }
}