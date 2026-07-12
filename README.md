# Java Torrent File Parser
A simple Java library for parsing and encoding `.torrent` files using the Bencode format.

## Requirements
- Java 17 or higher
- Maven (for building locally)

## Installation

Because this library is not currently published to Maven Central, you will need to install it to your local Maven repository first.

```bash
git clone https://github.com/robothaver/JavaTorrentFileParser
cd JavaTorrentFileParser
mvn clean install
```

**Maven:**
```xml
<dependency>
    <groupId>com.robothaver</groupId>
    <artifactId>TorrentFileParser</artifactId>
    <version>1.2</version>
</dependency>
```

## Features

### Parsing

- Parse `.torrent` files into a `TorrentMetadata` object
- Parse `.torrent` files into a `Map<String, Object>`
- Calculate the info hash
- Support for both single-file and multi-file torrents
- Access optional metadata fields (trackers, comments, creator info, Azureus properties and more)
- Reusable and thread-safe parser

### Encoding

- Encode a `Map<String, Object>` into a Bencoded byte array
- Encode a `TorrentMetadata` object into a Bencoded byte array
- Reusable and thread-safe encoder

## Raw byte and string handling
- If you parse into a `TorrentMetadata` object, the parser automatically converts known text fields into Java Strings. However, any unrecognized fields placed into the `otherValues` map will remain as raw byte arrays and must be converted manually.
- If you parse directly to a `Map<String, Object>`, all string values will remain as byte arrays and require manual conversion.

## Usage
### Parser usage
```java
public static void main(String[] args) throws IOException, MalformedTorrentFileException, NoSuchAlgorithmException {
    Path path = Path.of("Path to torrent file");
    byte[] bytes = Files.readAllBytes(path);
    Instant start = Instant.now();

    TorrentFileParser torrentParser = new TorrentFileParserImpl();
    
    // Parse to metadata object
    // If InfoHashCalculator is provided the info hash will get calculated
    TorrentMetadata torrentMetadata = torrentParser.parseToMetadata(bytes, new InfoHashCalculatorImpl());
    // Or use torrentParser.parseToMap() to parse to a map
    
    Instant finish = Instant.now();

    System.out.println(torrentMetadata.getName());
    long timeElapsed = Duration.between(start, finish).toMillis();
    System.out.println("Parsing finished in " + timeElapsed + "ms");
}
```

*Note: If you parse to a map and provide an `InfoHashCalculator`, the calculated info hash will get added to the root of the returned map.*

### Encoder usage
```java
public static void main(String[] args) throws NoSuchAlgorithmException, IOException, MalformedTorrentFileException {
    Path path = Path.of("Path to torrent file");
    TorrentFileParserImpl torrentFileParser = new TorrentFileParserImpl();
    byte[] fileBytes = Files.readAllBytes(path);

    // Encode a map to bencoded byte array (reencode a .torrent file in this example)
    Map<String, Object> torrentMap = torrentFileParser.parseToMap(fileBytes, null);
    TorrentEncoderImpl torrentEncoder = new TorrentEncoderImpl();
    byte[] bytes = torrentEncoder.encodeTorrentMap(torrentMap);

    // Encode metadata object to bencoded byte array
    TorrentMetadata torrentMetadata = torrentFileParser.parseToMetadata(fileBytes, null);
    byte[] metadataBytes = torrentEncoder.encodeTorrentMetadata(torrentMetadata);
}
```

## TorrentMetadata object structure
```java
public class TorrentMetadata {
    private final Map<String, Object> otherValues;
    private String announce;
    private String name;
    private Long pieceLength;
    private boolean isSingleFile;
    private boolean isPrivate;
    private Long totalLength;
    private byte[] pieces;
    private final List<TorrentFile> files;
    String infoHash; // null if no InfoHashCalculator is provided

    // Optional fields
    private List<List<String>> announceList;
    private String creator;
    private Long creationDate;
    private String source;
    private String comment;
    private String encoding;
    private Map<String, Object> azureusProperties;
}
```