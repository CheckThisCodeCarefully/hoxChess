/**
 *  Copyright 2016 Huy Phan <huyphan@playxiangqi.com>
 *
 *  This file is part of HOXChess.
 *
 *  HOXChess is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  HOXChess is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with HOXChess.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.playxiangqi.hoxchess;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This class manages the messages in the entire App.
 *
 */
public class MessageManager {

    private static final String TAG = "MessageManager";

    // The singleton instance.
    private static MessageManager instance_;

    // Member variables...
    private List<MessageInfo> messages_ = new ArrayList<MessageInfo>();

    // *************************************************************************************
    public interface EventListener {
        void onMessageReceived(MessageInfo messageInfo);
    }
    private Set<EventListener> listeners_ = new HashSet<EventListener>();

    public void addListener(EventListener listener) {
        listeners_.add(listener);
    }

    public void removeListener(EventListener listener) {
        listeners_.remove(listener);
    }

    // *************************************************************************************

    /**
     * Singleton API to return the instance.
     */
    public static MessageManager getInstance() {
        if (instance_ == null) {
            instance_ = new MessageManager();
        }
        return instance_;
    }

    /**
     * Constructor
     */
    public MessageManager() {
        Log.v(TAG, "[CONSTRUCTOR]: ...");
    }

    // ***************************************************************
    //
    //              Public APIs
    //
    // ***************************************************************

    public List<MessageInfo> getMessages() {
        return messages_;
    }

    public List<MessageInfo> getMessages(MessageInfo.MessageType messageType) {
        List<MessageInfo> foundMessages = new ArrayList<MessageInfo>();
        for (MessageInfo message : messages_) {
            if (message.type == messageType) {
                foundMessages.add(message);
            }
        }
        return foundMessages;
    }

    public int size() {
        return messages_.size();
    }

    public void addMessage(MessageInfo messageInfo) {
        messages_.add(messageInfo);
        Log.d(TAG, "addMessage: Notify listeners-size:" + listeners_.size());
        for (EventListener listener : listeners_) {
            listener.onMessageReceived(messageInfo);
        }
    }

    public void removeMessages(MessageInfo.MessageType messageType) {
        Iterator<MessageInfo> iterator = messages_.iterator();
        while (iterator.hasNext()) {
            final MessageInfo message = iterator.next();
            if (message.type == messageType) {
                iterator.remove();
            }
        }
    }


    // ***************************************************************
    //
    //              Private APIs
    //
    // ***************************************************************


}
