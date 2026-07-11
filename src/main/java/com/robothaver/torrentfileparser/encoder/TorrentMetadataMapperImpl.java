package com.robothaver.torrentfileparser.encoder;

import com.robothaver.torrentfileparser.domain.TorrentFile;
import com.robothaver.torrentfileparser.domain.TorrentMetadata;

import java.util.*;

public class TorrentMetadataMapperImpl implements TorrentMetadataMapper {
    @Override
    public Map<String, Object> metadataToMap(TorrentMetadata torrentMetadata) {
        Map<String, Object> torrentMap = new HashMap<>(torrentMetadata.getOtherValues());

        addTopLevelFields(torrentMetadata, torrentMap);
        Map<String, Object> infoDict = createInfoDictionary(torrentMetadata);

        torrentMap.put("info", infoDict);
        return torrentMap;
    }

    private void addTopLevelFields(TorrentMetadata torrentMetadata, Map<String, Object> torrentMap) {
        if (torrentMetadata.getAnnounce() != null) {
            torrentMap.put("announce", torrentMetadata.getAnnounce());
        }

        if (torrentMetadata.getAnnounceList() != null) {
            torrentMap.put("announce-list", torrentMetadata.getAnnounceList());
        }

        if (torrentMetadata.getCreator() != null) {
            torrentMap.put("created by", torrentMetadata.getCreator());
        }

        if (torrentMetadata.getCreationDate() != null) {
            torrentMap.put("creation date", torrentMetadata.getCreationDate());
        }

        if (torrentMetadata.getComment() != null) {
            torrentMap.put("comment", torrentMetadata.getComment());
        }

        if (torrentMetadata.getEncoding() != null) {
            torrentMap.put("encoding", torrentMetadata.getEncoding());
        }

        if (torrentMetadata.getAzureusProperties() != null) {
            torrentMap.put("azureus_properties", torrentMetadata.getAzureusProperties());
        }
    }

    private Map<String, Object> createInfoDictionary(TorrentMetadata torrentMetadata) {
        Map<String, Object> infoDict = new HashMap<>();

        infoDict.put("name", torrentMetadata.getName());
        infoDict.put("piece length", torrentMetadata.getPieceLength());
        infoDict.put("pieces", torrentMetadata.getPieces());

        if (torrentMetadata.isSingleFile()) {
            infoDict.put("length", torrentMetadata.getTotalLength());
        } else {
            List<Map<String, Object>> fileList = new ArrayList<>();

            for (TorrentFile file : torrentMetadata.getFiles()) {
                Map<String, Object> fileMap = new HashMap<>();

                fileMap.put("length", file.length());
                fileMap.put("path", Arrays.asList(file.path().split("/")));

                fileList.add(fileMap);
            }

            infoDict.put("files", fileList);
        }

        infoDict.put("private", torrentMetadata.isPrivate() ? 1 : 0);

        if (torrentMetadata.getSource() != null) {
            infoDict.put("source", torrentMetadata.getSource());
        }

        return infoDict;
    }
}
