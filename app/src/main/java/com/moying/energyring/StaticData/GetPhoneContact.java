package com.moying.energyring.StaticData;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.moying.energyring.Model.MemberEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by adinnet on 2016/1/22.
 * 获取手机的联系人
 */
public class GetPhoneContact {
    /**
     * 获得手机中的联系人列表
     *
     * @param mContext
     * @return
     */
    public static List<MemberEntity> queryContact(Context mContext) {
        List<MemberEntity> list = new ArrayList<MemberEntity>();
        MemberEntity contact;
        ContentResolver resolver = mContext.getContentResolver();
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        // 得到联系人头像Bitamp
        // 得到联系人头像ID
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                // Bitmap contactPhoto = null;
                // Long photoid =
                // phoneCursor.getLong(phoneCursor.getColumnIndex(Phone.PHOTO_ID));
                contact = new MemberEntity();
                contact.setId(phoneCursor.getLong(phoneCursor.getColumnIndex("contact_id")) + "");
                contact.setUser_name(phoneCursor.getString(phoneCursor .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                contact.setPhone(phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s*", ""));
                // photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
		/*
		 * if(photoid > 0 ) { Uri uri =
		 * ContentUris.withAppendedId(ContactsContract
		 * .Contacts.CONTENT_URI,contact.get_id()); InputStream input =
		 * ContactsContract
		 * .Contacts.openContactPhotoInputStream(resolver, uri);
		 * contactPhoto = BitmapFactory.decodeStream(input); }else {
		 * contactPhoto =
		 * BitmapFactory.decodeResource(mContext.getResources
		 * (),R.drawable.head_default); }
		 * contact.setContactPhoto(contactPhoto);
		 */
                list.add(contact);
            }
        }
        phoneCursor.close();
        phoneCursor = null;
        return list;
    }

    public static MemberEntity getContactById(Context mContext, long contactId) {
        MemberEntity contact = null;
        ContentResolver resolver = mContext.getContentResolver();
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                "contact_id = ? ", new String[]{contactId + ""}, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                // Bitmap contactPhoto = null;
                // Long photoid =
                // phoneCursor.getLong(phoneCursor.getColumnIndex(Phone.PHOTO_ID));
                contact = new MemberEntity();
                contact.setId(phoneCursor.getLong(phoneCursor
                        .getColumnIndex("contact_id")) + "");
                contact.setUser_name(phoneCursor.getString(phoneCursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                contact.setPhone(phoneCursor.getString(phoneCursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
		/*
		 * //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的 if(photoid > 0 ) {
		 * Uri uri =
		 * ContentUris.withAppendedId(ContactsContract.Contacts
		 * .CONTENT_URI,contact.get_id()); InputStream input =
		 * ContactsContract
		 * .Contacts.openContactPhotoInputStream(resolver, uri);
		 * contactPhoto = BitmapFactory.decodeStream(input); }else {
		 * contactPhoto =
		 * BitmapFactory.decodeResource(mContext.getResources
		 * (),R.drawable.head_default); }
		 * contact.setContactPhoto(contactPhoto);
		 */
            }
        }
        phoneCursor.close();
        phoneCursor = null;
        return contact;
    }
}