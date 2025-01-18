# Java torrent file parser
A simple java library that allows you to read the contents of a torrent file.

# Features

- Parse torrent file to a map
- Parse torrent file to a Torrent object

### Torrent object structure

    public class Torrent {
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