package com.calcdistanceapp.presentation.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.calcdistanceapp.domain.model.Station

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComposable(
    hintText: String,
    searchText: String,
    expanded: Boolean,
    searchResults: List<Station>,
    onExpandedChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    onSearchFinish: (Station) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(searchText) }

    Box(
        Modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = text,
                    onQueryChange = {
                        text = it
                        onSearch(text)
                    },
                    onSearch = { onExpandedChange(false) },
                    expanded = expanded,
                    onExpandedChange = {
                        onExpandedChange(it)
                        onSearch(text)
                    },
                    placeholder = { Text(text = hintText) },
                    leadingIcon = {
                        if (expanded) {
                            Icon(
                                modifier = Modifier.clickable {
                                    onExpandedChange(false)
                                },
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        } else {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    },
                    trailingIcon = {
                        if (expanded && text.isNotEmpty()) {
                            Icon(
                                modifier = Modifier.clickable {
                                    if (text.isNotEmpty()) {
                                        text = ""
                                        onSearch(text)
                                    } else {
                                        onExpandedChange(false)
                                    }
                                },
                                imageVector = Icons.Default.Clear, contentDescription = "delete text"
                            )
                        }
                    },
                )
            },
            expanded = expanded,
            onExpandedChange = onExpandedChange,
        ) {
            SearchItemScreenComposable(
                searchResults = searchResults,
                onClick = { station ->
                    text = station.name
                    onExpandedChange(false)
                    onSearchFinish(station)
                })
        }
    }
}