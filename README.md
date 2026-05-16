# Java Torrent File Parser
A simple Java library that allows you to read the contents of a `.torrent` file.

# Features
- Parse a .torrent file into a TorrentMetadata object
- Parse a .torrent file into a Map<String, Object>
- Get the info hash
- Supports single-file and multi-file torrents
- Access optional metadata fields such as trackers, comments, and creator info

### TorrentMetadata object structure
    public class TorrentMetadata {
        Map<String, Object> otherValues;
        String announce;
        Long totalLength;
        List<TorrentFile> files;
        String name;
        Long pieceLength;
        String pieces;
        boolean isSingleFile;
        boolean isPrivate;
        String infoHash; // null if computeInfoHash is set to false
    
        // Optional fields
        List<List<String>> announceList;
        String creator;
        Long creationDate;
        String source;
        String comment;
        String encoding;
        Map<String, Object> azureusProperties;
    }