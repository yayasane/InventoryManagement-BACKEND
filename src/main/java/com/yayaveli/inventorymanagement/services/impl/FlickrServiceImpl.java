package com.yayaveli.inventorymanagement.services.impl;

import java.io.InputStream;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.yayaveli.inventorymanagement.services.FlickrService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlickrServiceImpl implements FlickrService {

    private Flickr flickr;

    @Autowired
    public FlickrServiceImpl(Flickr flickr) {
        this.flickr = flickr;
    }

    @Override
    public String savePhoto(InputStream photo, String title) throws FlickrException {
        UploadMetaData uploadMetadata = new UploadMetaData();
        uploadMetadata.setTitle(title);

        String photoId = flickr.getUploader().upload(photo, uploadMetadata);

        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }

}
